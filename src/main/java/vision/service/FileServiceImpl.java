package vision.service;

import gate.Corpus;
import gate.DataStore;
import gate.Factory;
import gate.persist.PersistenceException;
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
        createDirectory(CommonUtils.TMP_FILES_PATH);
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

    private void createDirectory(String path) {
        if (!Files.isDirectory(Paths.get(path))) {
            if (new File(path).mkdir()) {
                logger.info("New user directory created");
            }
        }
    }

    public boolean saveCorpusToDatastore(Corpus corpus, File path) {
        try {
            DataStore dataStore = Factory.createDataStore("gate.persist.SerialDataStore", CommonUtils.getFileUrl(path).toString());
            dataStore.open();
            Corpus serializedCorpus = (Corpus) dataStore.adopt(corpus);
            serializedCorpus.setName("Parsed files saved by RecruiterVision");
            dataStore.sync(serializedCorpus);
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            return false;
        }
    }

}
