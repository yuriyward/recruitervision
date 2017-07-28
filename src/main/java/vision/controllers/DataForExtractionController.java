package vision.controllers;

import com.jfoenix.controls.JFXCheckBox;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import vision.service.ScreensManager;

/**
 * @author Yuriy on 28.07.2017.
 */
@FXMLController
public class DataForExtractionController {
    @Autowired
    ScreensManager screensManager;
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
    private JFXCheckBox summury;

    @FXML
    private JFXCheckBox interests;

    @FXML
    void allBasicClick(ActionEvent event) {

    }

    @FXML
    void allEducationalClick(ActionEvent event) {

    }

    @FXML
    void allInterestsClick(ActionEvent event) {

    }

    @FXML
    void allSkillsClick(ActionEvent event) {

    }

    @FXML
    void allSummuryClick(ActionEvent event) {

    }

    @FXML
    void allWorkClick(ActionEvent event) {

    }

    @FXML
    void backPageClick(ActionEvent event) {
        screensManager.showCvFilesWindow();
    }

    @FXML
    void checkAllClick(ActionEvent event) {

    }

    @FXML
    void nextPageClick(ActionEvent event) {
        screensManager.showEndFileWindow();
    }

    @FXML
    void uncheckAllClick(ActionEvent event) {

    }
}
