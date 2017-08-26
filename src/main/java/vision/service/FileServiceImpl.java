package vision.service;

import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Yuriy on 01.08.2017.
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public File saveAsHtml(String path) {
        return new File(path + File.separator + "extractedTEXT.txt");
    }
}
