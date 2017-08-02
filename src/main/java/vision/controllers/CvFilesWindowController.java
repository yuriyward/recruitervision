package vision.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import vision.Start;
import vision.service.FileServiceImpl;
import vision.service.ScreensManager;
import vision.service.TikaServiceImpl;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 24.07.2017.
 */
@FXMLController
public class CvFilesWindowController implements Initializable {
    @Autowired
    ScreensManager screensManager;
    @Autowired
    FileServiceImpl fileService;
    @Autowired
    TikaServiceImpl tikaService;

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

    ObservableList<File> observableFiles;
    final FileChooser fileChooser = new FileChooser();

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
        fileNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<File, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<File, String> param) {
                return new SimpleStringProperty(FilenameUtils.getBaseName(param.getValue().getPath()));
            }
        });
        fileLocationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<File, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<File, String> param) {
                return new SimpleStringProperty(param.getValue().getPath());
            }
        });
        fileExtensionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<File, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<File, String> param) {
                return new SimpleStringProperty(FilenameUtils.getExtension(param.getValue().getPath())
                );
            }
        });
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
        screensManager.showDataForExtractionWindow();
    }

    @FXML
    void backPageClick() {
        screensManager.showHomeWindow();
    }

    public ObservableList<File> getFiles() {
        return observableFiles;
    }

    public File getFirstFile() {
        return observableFiles.get(0);
    }

    @FXML
    void extractOnlyText() {
        File file = fileTable.getSelectionModel().getSelectedItem();
        if (file != null) {
            tikaService.parsePDFtoTEXT(file);
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
