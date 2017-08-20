package vision.service;

import vision.models.Filed;

import java.util.ArrayList;

/**
 * @author Yuriy on 18.08.2017.
 */
public interface GateService {
    void initGate();
    void initPlugins();
    void initNewCorpus();
    void addFileToCorpus(Filed filed);
    void executeController();
    void extractData();
}
