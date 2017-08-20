package vision.service;

import gate.*;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vision.models.Filed;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Yuriy on 18.08.2017.
 */
@Service
public class GateServiceImpl implements GateService {
    final static Logger logger = LoggerFactory.getLogger(GateServiceImpl.class);

    private CorpusController corpusController;
    private Corpus corpus;
    private final static String userDir = System.getProperty("user.dir");

    @Override
    public void initPlugins() {
        File annieGapp = new File(Gate.getGateHome(), "recruiterVision.gapp");
        try {
            corpusController = (CorpusController) PersistenceManager.loadObjectFromFile(annieGapp);
        } catch (PersistenceException | IOException | ResourceInstantiationException e) {
            e.printStackTrace();
        }
        logger.info("Annie inited");
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
            corpus.add(Factory.newDocument(filed.getFile().toURI().toURL()));
        } catch (ResourceInstantiationException | MalformedURLException e) {
            e.printStackTrace();
        }
        logger.info("New file added to corpus");
    }

    @Override
    public void initGate() {
        if (Gate.getGateHome() == null) {
            Gate.setGateHome(new File(String.format("%s\\src\\main\\resources\\gate", userDir)));
        }
        if (Gate.getPluginsHome() == null)
            Gate.setPluginsHome(new File(String.format("%s\\src\\main\\resources\\gate\\plugins", userDir)));
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
        Iterator iterator = corpus.iterator();
        while (iterator.hasNext()) {
            Document document = (Document) iterator.next();
            logger.info(document.getSourceUrl().getFile());
            AnnotationSet annotations = document.getAnnotations();
            AnnotationSet annotationSet = annotations.get("Address");
            Annotation annotation = annotationSet.iterator().next();
            String address = (String) annotation.getFeatures().get("kind");
            logger.info(address);
        }
        logger.info("Data extracted");
    }
}
