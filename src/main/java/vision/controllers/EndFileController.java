package vision.controllers;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import vision.service.ScreensManager;

/**
 * @author Yuriy on 28.07.2017.
 */
@FXMLController
public class EndFileController {
    @Autowired
    ScreensManager screensManager;
    @FXML
    void backPageClick(ActionEvent event) {
        screensManager.showDataForExtractionWindow();
    }

    @FXML
    void extractDOC(ActionEvent event) {

    }

    @FXML
    void extractPDF(ActionEvent event) {

    }

    @FXML
    void nextPageClick(ActionEvent event) {

    }

    @FXML
    void selectFolderBtn(ActionEvent event) {

    }

    @FXML
    void selectFolderFld(ActionEvent event) {

    }
}
