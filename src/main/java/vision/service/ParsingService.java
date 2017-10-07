package vision.service;

import java.io.File;
import java.util.List;

/**
 * @author Yuriy on 01.08.2017.
 */
public interface ParsingService {
    void parseAllFiles(List<File> files);

    void parseFile(File file);

    String parseToText(File file);

    String identifyLanguage(String text);

}
