package vision.service;

import java.io.File;

/**
 * @author Yuriy on 01.08.2017.
 */
public interface TikaService {
    void parsePDFtoTEXT(File file);

    String getParsedTEXT();

    String[] getMetedata();
}
