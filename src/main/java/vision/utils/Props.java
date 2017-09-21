package vision.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

/**
 * @author Yuriy on 20.09.2017.
 */
@Service
public class Props {
    @Getter
    private boolean PARSE_FILE_BY_TIKA;
    @Getter
    private String DEFAULT_PATH_CHK;
    @Getter @Setter
    private String DEFAULT_PATH;
    private static final String FILENAME = CommonUtils.RESOURCE_DIR + "\\config.properties";
    private final Properties prop;

    public Props() {
        prop = new Properties();
        loadPropertiesFile();
    }

    private void loadPropertiesFile() {
        FileInputStream input;
        try {
            input = new FileInputStream(FILENAME);
            prop.load(input);
            PARSE_FILE_BY_TIKA = Boolean.valueOf(prop.getProperty("PARSE_FILE_BY_TIKA"));
            DEFAULT_PATH = prop.getProperty("DEFAULT_PATH");
            DEFAULT_PATH_CHK = prop.getProperty("DEFAULT_PATH_CHK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void saveToPropertiesFile(String value, String key) {
        prop.setProperty(value, key);
        File file = new File(FILENAME);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            prop.store(fileOutputStream, "Program settings");
            loadPropertiesFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
