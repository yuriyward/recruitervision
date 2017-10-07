package vision.service;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vision.utils.CommonUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Yuriy on 01.08.2017.
 */
@Service
public class FileServiceImpl implements FileService {
    private final static String EXTENSION = ".txt";
    private final static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public File saveParsedText(String path, File file, String text) {
        String filename = FilenameUtils.getBaseName(file.getName()) + EXTENSION;
        createDirectory();
        String tmpFile = String.join(CommonUtils.DELIMITER, path, filename);
        try {
            FileWriter fileWriter = new FileWriter(tmpFile);
            fileWriter.write(text);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(tmpFile);
    }

    @Override
    public void removeFileFromUserDirectory(File file) {
        if (file.delete()) {
            logger.info("Temp file deleted from user directory " + file.getName());
        }
    }

    private void createDirectory() {
        if (!Files.isDirectory(Paths.get(CommonUtils.TMP_FILES_PATH))) {
            if (new File(CommonUtils.TMP_FILES_PATH).mkdir()) {
                logger.info("New user directory created");
            }
        }
    }

}
