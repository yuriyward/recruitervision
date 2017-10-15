package vision.service;

import gate.Corpus;

import java.io.File;

/**
 * @author Yuriy on 01.08.2017.
 */
public interface FileService {
    File saveParsedText(String path, File file, String text);

    void removeFileFromUserDirectory(File file);

    boolean saveCorpusToDatastore(Corpus corpus, File path);
}
