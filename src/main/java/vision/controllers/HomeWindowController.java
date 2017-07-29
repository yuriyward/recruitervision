package vision.controllers;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import vision.service.ScreensManager;

/**
 * @author Yuriy on 29.07.2017.
 */
@FXMLController
public class HomeWindowController {
    @Autowired
    ScreensManager screensManager;

    @FXML
    void nextPageClick() {
        screensManager.showCvFilesWindow();
    }


}
