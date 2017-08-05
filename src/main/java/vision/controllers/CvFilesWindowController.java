package vision.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import vision.Start;
import vision.service.FileService;
import vision.service.ScreensManager;
import vision.service.TikaService;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 24.07.2017.
 */
@FXMLController
public class CvFilesWindowController implements Initializable {
    private final ScreensManager screensManager;
    private final FileService fileService;
    private final TikaService tikaService;

    @FXML
    private JFXButton addCvFileID;
    @FXML
    private JFXButton removeCvFileID;
    @FXML
    private JFXCheckBox defaultPath;
    @FXML
    private TableView<File> fileTable;
    @FXML
    private TableColumn<File, String> fileNameColumn;
    @FXML
    private TableColumn<File, String> fileLocationColumn;
    @FXML
    private TableColumn<File, String> fileExtensionColumn;

    @Getter ObservableList<File> observableFiles;
    final FileChooser fileChooser = new FileChooser();

    @Autowired
    public CvFilesWindowController(ScreensManager screensManager, FileService fileService, TikaService tikaService) {
        this.screensManager = screensManager;
        this.fileService = fileService;
        this.tikaService = tikaService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configuringFileChooser(fileChooser);
        initTable();
    }

    @FXML
    void addCvFile() {
        if (defaultPath.isSelected()) {
            File file = new File("D:\\recruitervision\\handle_test");
            fileChooser.setInitialDirectory(file);
        }
        List<File> files = fileChooser.showOpenMultipleDialog(Start.getStage());
        if (files != null) {
            addFilesToTable(files);
            fileTable.setItems(observableFiles);
        }
    }

    private void configuringFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Select CV files (PDF or DOC)");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF, DOC, DOCX", "*.pdf", "*.doc", "*.docx"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("DOCX", "*.docx"),
                new FileChooser.ExtensionFilter("DOC", "*.doc"));
    }

    private void initTable() {
        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(FilenameUtils.getBaseName(param.getValue().getPath())));
        fileLocationColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPath()));
        fileExtensionColumn.setCellValueFactory(param -> new SimpleStringProperty(FilenameUtils.getExtension(param.getValue().getPath())));
        fileTable.getColumns().setAll(fileNameColumn, fileLocationColumn, fileExtensionColumn);
        observableFiles = FXCollections.observableArrayList();
    }

    private void addFilesToTable(List<File> files) {
        for (File file : files) {
            if (!observableFiles.contains(file)) {
                observableFiles.add(file);
            }
        }
    }

    @FXML
    void removeCvFile() {
        File file = fileTable.getSelectionModel().getSelectedItem();
        if (file != null) {
            observableFiles.remove(file);
        } else {
            showAlert();
        }
    }

    @FXML
    void nextPageClick() {
        tikaService.parseAllFiles(observableFiles);
        screensManager.showDataForExtractionWindow();
    }

    @FXML
    void backPageClick() {
        screensManager.showHomeWindow();
    }

    public File getFirstFile() {
        return observableFiles.get(0);
    }

    @FXML
    void extractOnlyText() {
        File file = fileTable.getSelectionModel().getSelectedItem();
        if (file != null) {
            tikaService.parse(file);
        } else {
            showAlert();
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(Start.getStage());
        alert.setTitle("File not selected");
        alert.setHeaderText("File not selected");
        alert.setContentText("Please select file for removing");
        alert.showAndWait();
    }

}
