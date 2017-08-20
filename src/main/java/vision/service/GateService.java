package vision.service;

import gate.creole.ExecutionException;
import vision.models.Filed;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Yuriy on 18.08.2017.
 */
public interface GateService {
    void initGate();
    void initPlugins();
    void createCorpus(ArrayList<Filed> fileds);
    void executeController() throws ExecutionException;
    void extractDataAndSave(File file);
}
