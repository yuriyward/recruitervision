package vision.controllers;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import vision.service.ScreensManager;

/**
 * @author Yuriy on 03.08.2017.
 */
@FXMLController
public class ParsedFilesController {
    @Autowired
    ScreensManager screensManager;

    @FXML
    private TableView<?> millingTable;

    @FXML
    private TableColumn<?, ?> fileName;

    @FXML
    private TableColumn<?, ?> fileLocation;

    @FXML
    private TableColumn<?, ?> fileExtension;

    @FXML
    private TableColumn<?, ?> fileStatus;

    @FXML
    private TableColumn<?, ?> fileStatus_parsed;

    @FXML
    private TableColumn<?, ?> fileStatus_extracted;

    @FXML
    void backPageClick() {
        screensManager.showEndFileWindow();
    }

    @FXML
    void nextPageClick() {

    }

    @FXML
    void showExtracted() {

    }

    @FXML
    void showParsed() {
        screensManager.showExploreDataWindow();
    }

}
