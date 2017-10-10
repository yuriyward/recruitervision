package vision.controllers;

import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import vision.javafx_own_components.TagBar;
import vision.repository.SelectionRepository;
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
    private VBox education;

    @FXML
    private VBox skills;

    @FXML
    private VBox summury;

    @FXML
    private VBox interests;

    @FXML
    private VBox accomplishments;

    @FXML
    private VBox languages;

    @FXML
    private VBox additionalInfo;

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
        TagBar baseBar = new TagBar();
        TagBar workBar = new TagBar();
        TagBar educationBar = new TagBar();
        TagBar skillsBar = new TagBar();
        TagBar summaryBar = new TagBar();
        TagBar interestsBar = new TagBar();
        TagBar accomplishmentsBar = new TagBar();
        TagBar languagesBar = new TagBar();
        TagBar additionalInfoBar = new TagBar();

        base.getChildren().addAll(baseBar);
        work.getChildren().addAll(workBar);
        education.getChildren().addAll(educationBar);
        skills.getChildren().addAll(skillsBar);
        summury.getChildren().addAll(summaryBar);
        interests.getChildren().addAll(interestsBar);
        accomplishments.getChildren().addAll(accomplishmentsBar);
        languages.getChildren().addAll(languagesBar);
        additionalInfo.getChildren().addAll(additionalInfoBar);

        baseBar.getTags().addListener((InvalidationListener) c -> SelectionRepository.advancedBase = baseBar.getTags());
        workBar.getTags().addListener((InvalidationListener) c -> SelectionRepository.advancedWork = workBar.getTags());
        educationBar.getTags().addListener((InvalidationListener) c -> SelectionRepository.advancedEducation = educationBar.getTags());
        skillsBar.getTags().addListener((InvalidationListener) c -> SelectionRepository.advancedSkills = skillsBar.getTags());
        summaryBar.getTags().addListener((InvalidationListener) c -> SelectionRepository.advancedSummary = summaryBar.getTags());
        interestsBar.getTags().addListener((InvalidationListener) c -> SelectionRepository.advancedInterests = interestsBar.getTags());
        accomplishmentsBar.getTags().addListener((InvalidationListener) c -> SelectionRepository.advancedAccomplishments = accomplishmentsBar.getTags());
        languagesBar.getTags().addListener((InvalidationListener) c -> SelectionRepository.advancedLanguages = languagesBar.getTags());
        additionalInfoBar.getTags().addListener((InvalidationListener) c -> SelectionRepository.advancedAdditionalInfo = additionalInfoBar.getTags());
    }
}
