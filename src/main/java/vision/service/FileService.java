package vision.service;

import java.io.File;

/**
 * @author Yuriy on 01.08.2017.
 */
public interface FileService {
    File saveParsedText(String path, File file, String text);

    void removeFileFromUserDirectory(File file);

    void saveParsedFileToFileRepository(File file, String text, String language, String parsedStatus);
}
