package vision.controllers;

import com.jfoenix.controls.JFXButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import vision.Start;
import vision.service.ScreensManager;
import vision.utils.OwnTagBar;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 29.07.2017.
 */
@FXMLController
public class AdvancedSelectionController implements Initializable {
    @Autowired
    ScreensManager screensManager;

    @FXML
    private VBox base;

    @FXML
    private VBox work;

    @FXML
    private VBox educational;

    @FXML
    private VBox skills;

    @FXML
    private VBox summury;

    @FXML
    private VBox interests;

    @FXML
    void backPageClick() {
        screensManager.showDataForExtractionWindow();
    }

    @FXML
    void nextPageClick() {
        screensManager.showEndFileWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTagBars();
    }

    private void loadTagBars() {
        OwnTagBar baseBar = new OwnTagBar();
        OwnTagBar workBar = new OwnTagBar();
        OwnTagBar educationalBar = new OwnTagBar();
        OwnTagBar skillsBar = new OwnTagBar();
        OwnTagBar summuryBar = new OwnTagBar();
        OwnTagBar interestsBar = new OwnTagBar();
        base.getChildren().addAll(baseBar);
        work.getChildren().addAll(workBar);
        educational.getChildren().addAll(educationalBar);
        skills.getChildren().addAll(skillsBar);
        summury.getChildren().addAll(summuryBar);
        interests.getChildren().addAll(interestsBar);
    }
}
