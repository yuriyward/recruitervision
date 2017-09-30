package vision.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import org.springframework.beans.factory.annotation.Autowired;
import vision.Start;
import vision.service.FileServiceImpl;
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
    private final FileServiceImpl fileService;

    @FXML
    private JFXTextField selectFolderFld;

    private DirectoryChooser directoryChooser;
    private File selectedDirectory;

    @Autowired
    public EndFileController(ScreensManager screensManager, FileServiceImpl fileService) {
        this.screensManager = screensManager;
        this.fileService = fileService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        folderValidator();
        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select folder where you want to save file after extraction...");
    }

    @FXML
    void selectFolderBtn() {
        selectedDirectory = directoryChooser.showDialog(Start.getStage());
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
    void extractDOC() {

    }

    @FXML
    void extractPDF() {
        if (folderExist()) {
            System.out.println("Parsed");
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
