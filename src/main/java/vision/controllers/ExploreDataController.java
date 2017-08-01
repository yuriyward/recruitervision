package vision.controllers;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;
import org.springframework.beans.factory.annotation.Autowired;
import vision.service.ScreensManager;

/**
 * @author Yuriy on 30.07.2017.
 */
@FXMLController
public class ExploreDataController {
    @Autowired
    ScreensManager screensManager;
    @FXML
    private HTMLEditor editorId;

    @FXML
    void backPageClick() {
        screensManager.showEndFileWindow();
    }

    @FXML
    void nextPageClick() {

    }
}
