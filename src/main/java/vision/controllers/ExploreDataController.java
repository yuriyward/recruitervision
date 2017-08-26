package vision.controllers;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vision.models.Filed;
import vision.service.ScreensManager;

/**
 * @author Yuriy on 30.07.2017.
 */
@FXMLController
public class ExploreDataController {
    private final static Logger logger = LoggerFactory.getLogger(ExploreDataController.class);

    private final ScreensManager screensManager;
    private Filed field;

    @FXML
    private HTMLEditor editorId;

    @Autowired
    public ExploreDataController(ScreensManager screensManager) {
        this.screensManager = screensManager;
    }

    public void setFiled(Filed filed) {
        this.field = filed;
        logger.info(filed.getParsed());
        Platform.runLater(() -> editorId.setHtmlText(filed.getParsed()));
    }
}
