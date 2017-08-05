package vision.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import org.springframework.beans.factory.annotation.Autowired;
import vision.Start;
import vision.service.FileServiceImpl;
import vision.service.ScreensManager;
import vision.service.TikaServiceImpl;

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
    private final TikaServiceImpl tikaService;
    private final CvFilesWindowController cvFilesWindowController;

    @FXML
    private JFXTextField selectFolderFld;

    private DirectoryChooser directoryChooser;
    private File selectedDirectory;

    @Autowired
    public EndFileController(ScreensManager screensManager, FileServiceImpl fileService, TikaServiceImpl tikaService, CvFilesWindowController cvFilesWindowController) {
        this.screensManager = screensManager;
        this.fileService = fileService;
        this.tikaService = tikaService;
        this.cvFilesWindowController = cvFilesWindowController;
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
    void backPageClick(ActionEvent event) {
        screensManager.showDataForExtractionWindow();
    }

    @FXML
    void nextPageClick(ActionEvent event) {
        screensManager.showParesedFiles();
    }

    @FXML
    void extractDOC(ActionEvent event) {

    }

    @FXML
    void extractPDF(ActionEvent event) {
        if (folderExist()) {
            tikaService.parse(cvFilesWindowController.getFirstFile());
            System.out.println("Parsed");
        }
    }

    private void folderValidator() {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        selectFolderFld.getValidators().add(requiredFieldValidator);
        requiredFieldValidator.setMessage("Please select destination folder");
        selectFolderFld.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    selectFolderFld.validate();
                }
            }
        });
        selectFolderFld.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectFolderFld.validate();
            }
        });
    }

    private boolean folderExist() {
        if (selectFolderFld.getText().length() != 0)
            return true;
        selectFolderFld.validate();
        return false;
    }
}
