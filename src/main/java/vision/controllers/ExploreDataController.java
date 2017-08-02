package vision.controllers;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.HTMLEditor;
import org.springframework.beans.factory.annotation.Autowired;
import vision.service.ScreensManager;
import vision.service.TikaServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 30.07.2017.
 */
@FXMLController
public class ExploreDataController implements Initializable {
    @Autowired
    ScreensManager screensManager;
    @Autowired
    TikaServiceImpl tikaService;

    @FXML
    private HTMLEditor editorId;

    @FXML
    void backPageClick() {
        screensManager.showEndFileWindow();
    }

    @FXML
    void nextPageClick() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editorId.setHtmlText(tikaService.getParsedTEXT());
    }
}
