package vision.controllers;

import com.jfoenix.controls.JFXButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import vision.Start;
import vision.service.ScreensManager;
import vision.view.CvFilesView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 22.07.2017.
 */
@FXMLController
public class MainWindowController {
    @Autowired
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
    private JFXButton exploreDataBtn;

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    @FXML
    private AnchorPane anchorPane;

    @FXML
    void homeBtnAct(ActionEvent event) {

    }

    @FXML
    void filesBtnAct(ActionEvent event) {
        screensManager.showCvFilesWindow();
    }

    @FXML
    void informationBtnAct(ActionEvent event) {

    }

    @FXML
    void exploreDataBtnAct(ActionEvent event) {

    }

    @FXML
    void endFileBtnAct(ActionEvent event) {

    }

}
