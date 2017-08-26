package vision.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Yuriy on 20.08.2017.
 */
public class CommonUtils {
    private final static String USER_DIR = System.getProperty("user.dir");
    private final static String GATE_HOME = "\\src\\main\\resources\\gate";
    private final static String DELIMITER = "\\";

    public static File getFileByPath() {
        return new File(String.join(DELIMITER, USER_DIR, GATE_HOME));
    }

    public static File getFileByPath(String pathToAdd) {
        return new File(String.join(DELIMITER, USER_DIR, GATE_HOME, pathToAdd));
    }

    public static URL getFileUrl(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
