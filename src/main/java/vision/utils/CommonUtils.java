package vision.utils;

import java.io.File;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Yuriy on 20.08.2017.
 */
public class CommonUtils {
    public final static String USER_DIR = System.getProperty("user.dir");
    private final static String GATE_HOME = "\\src\\main\\resources\\gate";
    final static String RESOURCE_DIR = USER_DIR + "\\src\\main\\resources";
    public final static String DELIMITER = "\\";
    public final static String TMP_FILES_PATH = String.join(CommonUtils.DELIMITER, CommonUtils.USER_DIR, "tmpFiles");

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

    public static String getIfNullEmptyString(String text) {
        if (text != null) {
            return text;
        } else {
            return "";
        }
    }

    public static String getIfNullEmptyString(Iterable<String> arr) {
        if (arr != null && arr.iterator().hasNext()) {
            return String.join(", ", arr);
        } else {
            return "";
        }
    }

}
