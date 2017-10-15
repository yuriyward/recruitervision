package vision.service;

import gate.Corpus;
import vision.models.Filed;

/**
 * @author Yuriy on 18.08.2017.
 */
public interface GateService {
    void initGate();
    void initPlugins();
    void initNewCorpus();
    void addFileToCorpus(Filed filed);
    void deleteFileFromCorpus(Filed filed);
    void executeController();
    void extractData();
    void clearCorpus();
    Corpus getCorpus();
}
