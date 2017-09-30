package vision.controllers;

import com.jfoenix.controls.JFXCheckBox;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import vision.javafx_own_components.AssociatedCheckBoxes;
import vision.service.ScreensManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 28.07.2017.
 */
@FXMLController
public class DataForExtractionController implements Initializable {
    private final ScreensManager screensManager;

    @FXML
    private JFXCheckBox allBasic;
    @FXML
    private JFXCheckBox allWork;
    @FXML
    private JFXCheckBox allSkills;
    @FXML
    private JFXCheckBox allEducational;
    @FXML
    private JFXCheckBox allSummary;
    @FXML
    private JFXCheckBox allInterests;

    @FXML
    private JFXCheckBox nameSurname;
    @FXML
    private JFXCheckBox email;
    @FXML
    private JFXCheckBox phone;
    @FXML
    private JFXCheckBox birthdate;
    @FXML
    private JFXCheckBox city;

    @FXML
    private JFXCheckBox work;
    @FXML
    private JFXCheckBox post;
    @FXML
    private JFXCheckBox date;
    @FXML
    private JFXCheckBox information;

    @FXML
    private JFXCheckBox skills;
    @FXML
    private JFXCheckBox knowledge;

    @FXML
    private JFXCheckBox place;
    @FXML
    private JFXCheckBox field;
    @FXML
    private JFXCheckBox dateEducational;

    @FXML
    private JFXCheckBox summary;

    @FXML
    private JFXCheckBox interests;

    @Autowired
    public DataForExtractionController(ScreensManager screensManager) {
        this.screensManager = screensManager;
    }

    @FXML
    void checkAllClick() {
        allBasic.setSelected(true);
        allWork.setSelected(true);
        allSkills.setSelected(true);
        allEducational.setSelected(true);
        allSummary.setSelected(true);
        allInterests.setSelected(true);
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
        allBasic.setSelected(false);
        allWork.setSelected(false);
        allSkills.setSelected(false);
        allEducational.setSelected(false);
        allSummary.setSelected(false);
        allInterests.setSelected(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AssociatedCheckBoxes basicChecks = new AssociatedCheckBoxes(allBasic, getBasicBoxes());
        AssociatedCheckBoxes workChecks = new AssociatedCheckBoxes(allWork, getWorkBoxes());
        AssociatedCheckBoxes skillsChecks = new AssociatedCheckBoxes(allSkills, getSkillsBoxes());
        AssociatedCheckBoxes educetionalBoxes = new AssociatedCheckBoxes(allEducational, getEducationalBoxes());
        AssociatedCheckBoxes summuryBoxes = new AssociatedCheckBoxes(allSummary, getSummuryBoxes());
        AssociatedCheckBoxes interestsBoxes = new AssociatedCheckBoxes(allInterests, getInterestsBoxes());

    }

    private List<JFXCheckBox> getBasicBoxes() {
        List<JFXCheckBox> basicBoxes = new ArrayList<>();
        basicBoxes.add(nameSurname);
        basicBoxes.add(email);
        basicBoxes.add(phone);
        basicBoxes.add(birthdate);
        basicBoxes.add(city);
        return basicBoxes;
    }

    private List<JFXCheckBox> getWorkBoxes() {
        List<JFXCheckBox> workBoxes = new ArrayList<>();
        workBoxes.add(work);
        workBoxes.add(post);
        workBoxes.add(date);
        workBoxes.add(information);
        return workBoxes;
    }

    private List<JFXCheckBox> getSkillsBoxes() {
        List<JFXCheckBox> skillsBoxes = new ArrayList<>();
        skillsBoxes.add(skills);
        skillsBoxes.add(knowledge);
        return skillsBoxes;
    }

    private List<JFXCheckBox> getEducationalBoxes() {
        List<JFXCheckBox> educationalBoxes = new ArrayList<>();
        educationalBoxes.add(place);
        educationalBoxes.add(field);
        educationalBoxes.add(dateEducational);
        return educationalBoxes;
    }

    private List<JFXCheckBox> getSummuryBoxes() {
        List<JFXCheckBox> summuryBoxes = new ArrayList<>();
        summuryBoxes.add(summary);
        return summuryBoxes;
    }


    private List<JFXCheckBox> getInterestsBoxes() {
        List<JFXCheckBox> interestsBoxes = new ArrayList<>();
        interestsBoxes.add(interests);
        return interestsBoxes;
    }

}
