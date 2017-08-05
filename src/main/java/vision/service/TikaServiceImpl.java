package vision.service;

import javafx.collections.ObservableList;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import vision.controllers.ParsedFilesController;
import vision.models.Filed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Yuriy on 01.08.2017.
 */
@Service
public class TikaServiceImpl implements TikaService {
    private FileInputStream inputstream;
    private String parsedStatus = "OK";
    private String extractedStatus = "In queue";
    private final ParsedFilesController parsedFilesController;

    @Autowired
    TikaServiceImpl(ParsedFilesController parsedFilesController) {
        this.parsedFilesController = parsedFilesController;
    }

    @Override
    public void parseAllFiles(ObservableList<File> files) {
        new Thread(() -> {
            if (files.size() > 0) {
                for (File file : files) {
                    parsedFilesController.addFiledToTable(new Filed(file, parse(file), parsedStatus, null, extractedStatus));
                }
            }
        }).start();
    }

    @Override
    public String parse(File file) {
        try {
            inputstream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        AutoDetectParser parser = new AutoDetectParser();
        try {
            parser.parse(inputstream, handler, metadata, pcontext);
            return handler.toString();
        } catch (IOException e) {
            parsedStatus = "IO error";
            e.printStackTrace();
        } catch (SAXException e) {
            parsedStatus = "SAX error";
            e.printStackTrace();
        } catch (TikaException e) {
            parsedStatus = "Tika error";
            e.printStackTrace();
        }
        return null;
    }
}
