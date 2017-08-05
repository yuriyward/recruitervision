package vision.controllers;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import vision.javafx_own_components.OwnTagBar;
import vision.service.ScreensManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 29.07.2017.
 */
@FXMLController
public class AdvancedSelectionController implements Initializable {
    private final ScreensManager screensManager;

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

    @Autowired
    public AdvancedSelectionController(ScreensManager screensManager) {
        this.screensManager = screensManager;
    }

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
