package vision.service;

import gate.Corpus;
import gate.CorpusController;
import gate.Factory;
import gate.Gate;
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

/**
 * @author Yuriy on 18.08.2017.
 */
@Service
public class GateServiceImpl implements GateService {
    final static Logger logger = LoggerFactory.getLogger(GateServiceImpl.class);

    private CorpusController corpusController;
    private Corpus corpus;
    private String userDir = System.getProperty("user.dir");

    @Override
    public void initAnnie() {
        File pluginsHome = Gate.getPluginsHome();
        File anniePlugin = new File(pluginsHome, "RecriuterVision");
        File annieGapp = new File(anniePlugin, "RecruiterVision.gapp");
        try {
            corpusController = (CorpusController) PersistenceManager.loadObjectFromFile(annieGapp);
        } catch (PersistenceException | IOException | ResourceInstantiationException e) {
            e.printStackTrace();
        }
        logger.info("Annie inited");
    }

    @Override
    public void createCorpus(ArrayList<Filed> fileds) {
        try {
            corpus = Factory.newCorpus("Transient Gate Corpus");
        } catch (ResourceInstantiationException e) {
            e.printStackTrace();
        }
        for (Filed filed : fileds) {
            try {
                corpus.add(Factory.newDocument(filed.getFile().toURI().toURL()));
            } catch (ResourceInstantiationException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
        logger.info("Сorpus created");
    }

    @Override
    public void initGate() {
        if (Gate.getGateHome() == null) {
            Gate.setGateHome(new File(String.format("%s\\src\\main\\resources\\GATE-files", userDir)));
        }
        if (Gate.getPluginsHome() == null)
            Gate.setPluginsHome(new File(String.format("%s\\src\\main\\resources\\GATE-files\\plugins", userDir)));
        try {
            Gate.init();
        } catch (GateException e) {
            e.printStackTrace();
        }
        logger.info("Gate inited");
    }

    @Override
    public void executeController() throws ExecutionException {
        corpusController.execute();
    }

    @Override
    public void extractDataAndSave(File file) {

    }
}
