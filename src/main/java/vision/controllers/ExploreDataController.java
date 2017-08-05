package vision.controllers;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;
import org.springframework.beans.factory.annotation.Autowired;
import vision.models.Filed;
import vision.service.ScreensManager;

/**
 * @author Yuriy on 30.07.2017.
 */
@FXMLController
public class ExploreDataController {
    private final ScreensManager screensManager;
    private Filed field;

    @FXML
    private HTMLEditor editorId;

    @Autowired
    public ExploreDataController(ScreensManager screensManager) {
        this.screensManager = screensManager;
    }

    @FXML
    void closeWindow() {
        screensManager.closeExploreDataWindow();
    }

    public void setFiled(Filed filed) {
        this.field = filed;
        Platform.runLater(() -> editorId.setHtmlText(filed.getParsed()));
    }
}
