package vision.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import vision.own_components.TagBar;
import vision.models.CV;
import vision.models.Filed;
import vision.utils.CommonUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 28.09.2017.
 */
@FXMLController
public class ParsedFileDataController implements Initializable {
    private CV cv;
    @FXML
    private JFXTextField candidateName;
    @FXML
    private JFXTextField candidateMiddleName;
    @FXML
    private JFXTextField candidateSurname;
    @FXML
    private JFXTextField gender;
    @FXML
    private JFXTextField addresses;
    @FXML
    private JFXTextField emailsTxtFld;
    @FXML
    private VBox emailsVBox;
    @FXML
    private JFXTextField city;
    @FXML
    private JFXTextField country;
    @FXML
    private JFXTextField phonesTxtFld;
    @FXML
    private VBox phonesVBox;
    @FXML
    private JFXTextField urlTxtFLd;
    @FXML
    private VBox urlVBox;
    @FXML
    private JFXTextField jobTitlesTxtFld;
    @FXML
    private VBox jobTitlesVBox;

    @FXML
    private JFXTextArea summaryTxt;

    @FXML
    private JFXTextArea exprerienceTitlesTxt;
    @FXML
    private JFXTextArea experienceTxt;

    @FXML
    private JFXTextArea skillsTxt;
    @FXML
    private JFXTextArea programmingLanguagesTxt;
    @FXML
    private JFXTextArea programmingSkillsTxt;

    @FXML
    private JFXTextArea educationTxt;

    @FXML
    private JFXTextArea languagesTxt;

    @FXML
    private JFXTextArea interestsTxt;

    @FXML
    private JFXTextArea additionalInfoTxt;

    @FXML
    private JFXTextArea credibilityTxt;

    @FXML
    private JFXTextArea awardsTxt;

    @FXML
    private JFXTextArea accomplishmentsTxt;

    private TagBar emailsBar;
    private TagBar phonesBar;
    private TagBar jobTitlesBar;
    private TagBar urlBar;

    @FXML
    void emailsChkBox() {
        emailsVBox.setVisible(!emailsVBox.isVisible());
        emailsTxtFld.setVisible(!emailsTxtFld.isVisible());
    }

    @FXML
    void jobTitlesChkBox() {
        jobTitlesVBox.setVisible(!jobTitlesVBox.isVisible());
        jobTitlesTxtFld.setVisible(!jobTitlesTxtFld.isVisible());
    }

    @FXML
    void phonesChkBox() {
        phonesVBox.setVisible(!phonesVBox.isVisible());
        phonesTxtFld.setVisible(!phonesTxtFld.isVisible());
    }

    @FXML
    void urlChkBox() {
        urlVBox.setVisible(!urlVBox.isVisible());
        urlTxtFLd.setVisible(!urlTxtFLd.isVisible());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createBars();
    }

    public void setFieldData(Filed field) {
        this.cv = field.getExtractedData();
        Platform.runLater(() -> {
            clearComponents();
            fillComponents();
        });
    }

    private void createBars() {
        emailsBar = new TagBar(true);
        emailsVBox.getChildren().addAll(emailsBar);
        phonesBar = new TagBar(true);
        phonesVBox.getChildren().addAll(phonesBar);
        jobTitlesBar = new TagBar(true);
        jobTitlesVBox.getChildren().addAll(jobTitlesBar);
        urlBar = new TagBar(true);
        urlVBox.getChildren().addAll(urlBar);

    }

    private void clearComponents() {
        emailsBar.clearBar();
        phonesBar.clearBar();
        jobTitlesBar.clearBar();
        urlBar.clearBar();
    }

    private void fillComponents() {
        // Base data
        candidateName.setText(CommonUtils.getIfNullEmptyString(cv.getCandidateName()));
        candidateMiddleName.setText(CommonUtils.getIfNullEmptyString(cv.getCandidateMiddleName()));
        candidateSurname.setText(CommonUtils.getIfNullEmptyString(cv.getCandidateSurname()));
        gender.setText(CommonUtils.getIfNullEmptyString(cv.getGender()));
        // Emails
        emailsTxtFld.setText(CommonUtils.getIfNullEmptyString(cv.getEmails()));
        emailsTxtFld.setVisible(false);
        emailsBar.addStringsToBar(cv.getEmails());
        // Phones
        phonesTxtFld.setText(CommonUtils.getIfNullEmptyString(cv.getPhones()));
        phonesTxtFld.setVisible(false);
        phonesBar.addStringsToBar(cv.getPhones());
        //
        addresses.setText(CommonUtils.getIfNullEmptyString(cv.getAddresses()));
        city.setText(CommonUtils.getIfNullEmptyString(cv.getCity()));
        country.setText(CommonUtils.getIfNullEmptyString(cv.getCountry()));
        // Job titles
        jobTitlesTxtFld.setText(CommonUtils.getIfNullEmptyString(cv.getCandidateJobTitles()));
        jobTitlesTxtFld.setVisible(false);
        jobTitlesBar.addStringsToBar(cv.getCandidateJobTitles());
        // URLs
        urlTxtFLd.setText(CommonUtils.getIfNullEmptyString(cv.getURLs()));
        urlTxtFLd.setVisible(false);
        // Summary
        summaryTxt.setText(CommonUtils.getIfNullEmptyString(cv.getSummarySection()));
        // Experience
        exprerienceTitlesTxt.setText(CommonUtils.getIfNullEmptyString(cv.getExperienceMain()));
        experienceTxt.setText(CommonUtils.getIfNullEmptyString(cv.getExperienceText()));
        // Skills
        skillsTxt.setText(CommonUtils.getIfNullEmptyString(cv.getSkillsSection()));
        programmingLanguagesTxt.setText(CommonUtils.getIfNullEmptyString(cv.getProgrammingLanguages()));
        programmingSkillsTxt.setText(CommonUtils.getIfNullEmptyString(cv.getProgrammingSkills()));
        // Education
        educationTxt.setText(CommonUtils.getIfNullEmptyString(cv.getEducationSection()));
        // Languages
        languagesTxt.setText(CommonUtils.getIfNullEmptyString(cv.getLanguagesSection()));
        // Interests
        interestsTxt.setText(CommonUtils.getIfNullEmptyString(cv.getInterests()));
        // Additional info
        additionalInfoTxt.setText(CommonUtils.getIfNullEmptyString(cv.getAdditionalInfo()));
        // Credibility
        credibilityTxt.setText(CommonUtils.getIfNullEmptyString(cv.getCredibility()));
        // Awards
        awardsTxt.setText(CommonUtils.getIfNullEmptyString(cv.getAwards()));
        // Accomplishments
        accomplishmentsTxt.setText(CommonUtils.getIfNullEmptyString(cv.getAccomplishments()));
    }


}
