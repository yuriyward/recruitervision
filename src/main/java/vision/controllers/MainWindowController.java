package vision.controllers;

import com.jfoenix.controls.JFXButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import vision.service.ScreensManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 22.07.2017.
 */
@FXMLController
public class MainWindowController implements Initializable {
    private final
    ScreensManager screensManager;
    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton filesBtn;

    @FXML
    private JFXButton informationBtn;

    @FXML
    private JFXButton endFileBtn;

    @FXML
    private JFXButton advancedSelectionId;

    @FXML
    private JFXButton processingId;

    @FXML
    private JFXButton showParsedDataBtn;

    @FXML
    @Getter private AnchorPane anchorPane;

    @FXML
    @Getter private StackPane stackPane;

    @Autowired
    public MainWindowController(ScreensManager screensManager) {
        this.screensManager = screensManager;
    }

    @FXML
    void homeBtnAct(ActionEvent event) {
        screensManager.showHomeWindow();
    }

    @FXML
    void filesBtnAct(ActionEvent event) {
        screensManager.showCvFilesWindow();
    }

    @FXML
    void informationBtnAct(ActionEvent event) {
        screensManager.showDataForExtractionWindow();
    }

    @FXML
    void advancedSelectionBtn(ActionEvent event) {
        screensManager.showAdvancedSelectionWindow();
    }

    @FXML
    void processingBtn(ActionEvent event) {

    }

    @FXML
    void endFileBtnAct(ActionEvent event) {
        screensManager.showEndFileWindow();
    }

    @FXML
    void showParsedData(ActionEvent event) {
        screensManager.showParesedFiles();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        screensManager.showHomeWindow();
    }
}
