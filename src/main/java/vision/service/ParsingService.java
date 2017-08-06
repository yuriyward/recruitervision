package vision.service;

import javafx.collections.ObservableList;

import java.io.File;

/**
 * @author Yuriy on 01.08.2017.
 */
public interface ParsingService {
    void parseAllFiles(ObservableList<File> files);

    String parseToText(File file);

    String identifyLanguage(String text);

}
