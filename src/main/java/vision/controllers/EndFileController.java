package vision.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vision.Start;
import vision.service.JSONCreator;
import vision.service.PDFCreator;
import vision.service.ScreensManager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 28.07.2017.
 */
@FXMLController
public class EndFileController implements Initializable {
    private final ScreensManager screensManager;
    private final PDFCreator pdfCreator;
    private final JSONCreator jsonCreator;
    private final static Logger logger = LoggerFactory.getLogger(EndFileController.class);

    @FXML
    private JFXTextField selectFolderFld;

    private DirectoryChooser directoryChooser;

    @Autowired
    public EndFileController(ScreensManager screensManager, PDFCreator pdfCreator, JSONCreator jsonCreator) {
        this.screensManager = screensManager;
        this.pdfCreator = pdfCreator;
        this.jsonCreator = jsonCreator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        folderValidator();
        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select folder where you want to save file after extraction...");
    }

    @FXML
    void selectFolderBtn() {
        File selectedDirectory = directoryChooser.showDialog(Start.getStage());
        if (selectedDirectory != null)
            selectFolderFld.setText(selectedDirectory.getPath());
    }

    @FXML
    void backPageClick() {
        screensManager.showAdvancedSelectionWindow();
    }

    @FXML
    void nextPageClick() {
        screensManager.showHomeWindow();
    }

    @FXML
    void extractPDF() {
        if (folderExist()) {
            if (pdfCreator.createDocument(selectFolderFld.getText())) {
                screensManager.showMaterialDialogForCandidatesList(selectFolderFld.getText());
            } else {
                screensManager.showMaterialDialog("Error during creation of candidates list", "File not created, please restart app and repeat", "OK");
            }
            logger.info("Candidate list created");
        }
    }

    @FXML
    void extractJSON() {
        if (folderExist()) {
            if (jsonCreator.createJsonFile(selectFolderFld.getText())) {
                screensManager.showMaterialDialogForCandidatesList(selectFolderFld.getText());
            } else {
                screensManager.showMaterialDialog("Error during creation a .json file", "File not created, please restart app and repeat", "OK");
            }
            logger.info("The .json file created");
        }
    }

    private void folderValidator() {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        selectFolderFld.getValidators().add(requiredFieldValidator);
        requiredFieldValidator.setMessage("Please select destination folder");
        selectFolderFld.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                selectFolderFld.validate();
            }
        });
        selectFolderFld.textProperty().addListener((observable, oldValue, newValue) -> selectFolderFld.validate());
    }

    private boolean folderExist() {
        if (selectFolderFld.getText().length() != 0)
            return true;
        selectFolderFld.validate();
        return false;
    }
}
