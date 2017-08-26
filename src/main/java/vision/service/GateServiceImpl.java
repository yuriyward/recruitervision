package vision.service;

import gate.*;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vision.models.CV;
import vision.models.Filed;
import vision.repository.FiledRepository;
import vision.utils.CommonUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Yuriy on 18.08.2017.
 */
@Service
public class GateServiceImpl implements GateService {
    private final static Logger logger = LoggerFactory.getLogger(GateServiceImpl.class);

    private final FiledRepository repository;
    private CorpusController corpusController;
    private Corpus corpus;

    @Autowired
    public GateServiceImpl(FiledRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initPlugins() {
        File annieGapp = new File(Gate.getGateHome(), "recruiterVision.gapp");
        try {
            corpusController = (CorpusController) PersistenceManager.loadObjectFromFile(annieGapp);
        } catch (PersistenceException | IOException | ResourceInstantiationException e) {
            e.printStackTrace();
        }
        logger.info("Annie and plugins inited");
    }

    @Override
    public void initNewCorpus() {
        try {
            corpus = Factory.newCorpus("Transient Gate Corpus");
        } catch (ResourceInstantiationException e) {
            e.printStackTrace();
        }
        logger.info("New corpus inited");
    }

    @Override
    public void addFileToCorpus(Filed filed) {
        try {
            corpus.add(Factory.newDocument(CommonUtils.getFileUrl(filed.getFile())));
        } catch (ResourceInstantiationException e) {
            e.printStackTrace();
        }
        logger.info("New file added to corpus");
    }

    @Override
    public void initGate() {
        if (Gate.getGateHome() == null) {
            Gate.setGateHome(CommonUtils.getFileByPath());
        }
        if (Gate.getPluginsHome() == null)
            Gate.setPluginsHome(CommonUtils.getFileByPath("plugins"));
        try {
            Gate.init();
        } catch (GateException e) {
            e.printStackTrace();
        }
        logger.info("Gate inited");
    }

    @Override
    public void executeController() {
        try {
            corpusController.setCorpus(corpus);
            corpusController.execute();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        logger.info("Controller executed");
    }

    @Override
    public void extractData() {
        for (Document document : corpus) {
            Filed filed = repository.getFiledByPath(document.getSourceUrl().getPath());
            CV cv = new CV();

            AnnotationSet annotations = document.getAnnotations();
            AnnotationSet annotationSet = annotations.get("NameFinder");
            Annotation annotation = annotationSet.iterator().next();
            cv.setCandidateName((String) annotation.getFeatures().get("firstName"));
            cv.setCandidateSurname((String) annotation.getFeatures().get("surname"));

            filed.setExtractedData(cv);
            logger.info(cv.toString());
        }
        logger.info("Data extracted");
    }
}
