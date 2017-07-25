package vision.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import vision.Start;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 24.07.2017.
 */
@FXMLController
public class CvFilesWindowController implements Initializable {
    @FXML
    private JFXButton addCvFileID;
    @FXML
    private JFXButton removeCvFileID;
    @FXML
    private TableView<?> fileTable;
    @FXML
    private TableColumn<?, ?> fileNameColumn;
    @FXML
    private TableColumn<?, ?> fileLocationColumn;
    @FXML
    private TableColumn<?, ?> fileExtensionColumn;


    final FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configuringFileChooser(fileChooser);

    }

    @FXML
    void addCvFile(ActionEvent event) {
        List<File> files = fileChooser.showOpenMultipleDialog(Start.getStage());

    }

    private void configuringFileChooser(FileChooser fileChooser){
        fileChooser.setTitle("Select CV files (PDF or DOC)");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF, DOC, DOCX", "*.pdf","*.doc","*.docx"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("DOCX", "*.docx"),
                new FileChooser.ExtensionFilter("DOC", "*.doc"));
    }

    @FXML
    void removeCvFile(ActionEvent event) {

    }



}
