package vision.service;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Yuriy on 01.08.2017.
 */
@Service
public class TikaServiceImpl implements TikaService {
    BodyContentHandler handler;
    Metadata metadata;
    FileInputStream inputstream;
    ParseContext pcontext;

    TikaServiceImpl() {
        handler = new BodyContentHandler();
        metadata = new Metadata();
        pcontext = new ParseContext();
    }

    @Override
    public void parsePDFtoTEXT(File file) {
        try {
            inputstream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PDFParser pdfparser = new PDFParser();
        try {
            pdfparser.parse(inputstream, handler, metadata, pcontext);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getParsedTEXT() {
        return handler.toString();
    }

    @Override
    public String[] getMetedata() {
        return metadata.names();
    }
}
