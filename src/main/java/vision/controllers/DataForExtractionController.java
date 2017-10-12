package vision.controllers;

import com.jfoenix.controls.JFXCheckBox;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import vision.own_components.AssociatedCheckBoxes;
import vision.repository.SelectionRepository;
import vision.service.ScreensManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 28.07.2017.
 */
@FXMLController
public class DataForExtractionController implements Initializable {
    private final ScreensManager screensManager;

    @FXML
    private JFXCheckBox nameSurname;
    @FXML
    private JFXCheckBox email;
    @FXML
    private JFXCheckBox jobTitles;
    @FXML
    private JFXCheckBox cityCountry;
    @FXML
    private JFXCheckBox urls;
    @FXML
    private JFXCheckBox gender;
    @FXML
    private JFXCheckBox phones;
    @FXML
    private JFXCheckBox allBasic;

    @FXML
    private JFXCheckBox experiencesTitles;
    @FXML
    private JFXCheckBox experoence;
    @FXML
    private JFXCheckBox allWork;

    @FXML
    private JFXCheckBox skills;
    @FXML
    private JFXCheckBox programmingLanguages;
    @FXML
    private JFXCheckBox programmingSkills;
    @FXML
    private JFXCheckBox allSkills;

    @FXML
    private JFXCheckBox education;
    @FXML
    private JFXCheckBox allEducational;

    @FXML
    private JFXCheckBox summary;
    @FXML
    private JFXCheckBox allSummary;

    @FXML
    private JFXCheckBox languages;
    @FXML
    private JFXCheckBox allLanguages;

    @FXML
    private JFXCheckBox accomplishments;
    @FXML
    private JFXCheckBox awards;
    @FXML
    private JFXCheckBox credibility;
    @FXML
    private JFXCheckBox allAccomplishments;

    @FXML
    private JFXCheckBox interests;
    @FXML
    private JFXCheckBox allInterests;

    @FXML
    private JFXCheckBox additionalInfo;
    @FXML
    private JFXCheckBox allAdditionalInfo;

    @Autowired
    public DataForExtractionController(ScreensManager screensManager) {
        this.screensManager = screensManager;
    }

    @FXML
    void checkAllClick() {
        if (!allBasic.isSelected()) allBasic.setSelected(true);
        if (!allWork.isSelected()) allWork.setSelected(true);
        if (!allSkills.isSelected()) allSkills.setSelected(true);
        if (!allEducational.isSelected()) allEducational.setSelected(true);
        if (!allSummary.isSelected()) allSummary.setSelected(true);
        if (!allInterests.isSelected()) allInterests.setSelected(true);
        if (!allLanguages.isSelected()) allLanguages.setSelected(true);
        if (!allAccomplishments.isSelected()) allAccomplishments.setSelected(true);
        if (!allAdditionalInfo.isSelected()) allAdditionalInfo.setSelected(true);
    }

    @FXML
    void backPageClick() {
        screensManager.showParesedFiles();
    }

    @FXML
    void nextPageClick() {
        screensManager.showAdvancedSelectionWindow();
    }

    @FXML
    void uncheckAllClick() {
        if (allBasic.isSelected()) allBasic.setSelected(false);
        if (allWork.isSelected()) allWork.setSelected(false);
        if (allSkills.isSelected()) allSkills.setSelected(false);
        if (allEducational.isSelected()) allEducational.setSelected(false);
        if (allSummary.isSelected()) allSummary.setSelected(false);
        if (allInterests.isSelected()) allInterests.setSelected(false);
        if (allLanguages.isSelected()) allLanguages.setSelected(false);
        if (allAccomplishments.isSelected()) allAccomplishments.setSelected(false);
        if (allAdditionalInfo.isSelected()) allAdditionalInfo.setSelected(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createAssociations();
        addListenersForCheckboxes();
    }

    private void createAssociations() {
        new AssociatedCheckBoxes(allBasic, createChkBoxList(nameSurname, email, jobTitles, cityCountry, urls, gender, phones));
        new AssociatedCheckBoxes(allWork, createChkBoxList(experiencesTitles, experoence));
        new AssociatedCheckBoxes(allSkills, createChkBoxList(skills, programmingLanguages, programmingSkills));
        new AssociatedCheckBoxes(allEducational, createChkBoxList(education));
        new AssociatedCheckBoxes(allSummary, createChkBoxList(summary));
        new AssociatedCheckBoxes(allInterests, createChkBoxList(interests));
        new AssociatedCheckBoxes(allLanguages, createChkBoxList(languages));
        new AssociatedCheckBoxes(allAccomplishments, createChkBoxList(accomplishments, awards, credibility));
        new AssociatedCheckBoxes(allAdditionalInfo, createChkBoxList(additionalInfo));
    }

    private void addListenersForCheckboxes() {
        nameSurname.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.nameSurname = newValue);
        email.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.email = newValue);
        jobTitles.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.jobTitles = newValue);
        cityCountry.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.cityCountry = newValue);
        urls.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.urls = newValue);
        gender.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.gender = newValue);
        phones.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.phones = newValue);

        experiencesTitles.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.experiencesTitles = newValue);
        experoence.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.experience = newValue);

        skills.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.skills = newValue);
        programmingLanguages.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.programmingLanguages = newValue);
        programmingSkills.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.programmingSkills = newValue);

        education.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.education = newValue);

        summary.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.summary = newValue);

        languages.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.languages = newValue);

        accomplishments.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.accomplishments = newValue);
        awards.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.awards = newValue);
        credibility.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.credibility = newValue);

        interests.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.interests = newValue);

        additionalInfo.selectedProperty().addListener((observable, oldValue, newValue) -> SelectionRepository.additionalInfo = newValue);
    }

    private List<JFXCheckBox> createChkBoxList(JFXCheckBox... jfxCheckBoxs) {
        List<JFXCheckBox> boxes = new ArrayList<>();
        boxes.addAll(Arrays.asList(jfxCheckBoxs));
        return boxes;
    }

}
