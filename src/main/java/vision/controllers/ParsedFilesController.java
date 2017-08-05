package vision.controllers;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import vision.Start;
import vision.models.Filed;
import vision.service.ScreensManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 03.08.2017.
 */
@FXMLController
public class ParsedFilesController implements Initializable {
    private final ScreensManager screensManager;

    @FXML
    private TableView<Filed> millingTable;

    @FXML
    private TableColumn<Filed, String> fileName;

    @FXML
    private TableColumn<Filed, String> fileLocation;

    @FXML
    private TableColumn<Filed, String> fileExtension;

    @FXML
    private TableColumn fileStatus;

    @FXML
    private TableColumn<Filed, String> fileStatus_parsed;

    @FXML
    private TableColumn<Filed, String> fileStatus_extracted;

    ObservableList<Filed> observableFiles = FXCollections.observableArrayList();


    @Autowired
    public ParsedFilesController(ScreensManager screensManager) {
        this.screensManager = screensManager;
    }

    @FXML
    void backPageClick() {
        screensManager.showEndFileWindow();
    }

    @FXML
    void nextPageClick() {

    }

    @FXML
    void showExtracted() {

    }

    @FXML
    void showParsed() {
        Filed filed = millingTable.getSelectionModel().getSelectedItem();
        if (filed != null) {
            screensManager.showExploreDataWindow(filed);
        } else {
            screensManager.showMaterialDialog("File not selected", "Please select file for showing", "OK");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initRightClick();
    }

    private void initTable() {
        fileName.setCellValueFactory(param -> new SimpleStringProperty(FilenameUtils.getBaseName(param.getValue().getFile().getPath())));
        fileLocation.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFile().getPath()));
        fileExtension.setCellValueFactory(param -> new SimpleStringProperty(FilenameUtils.getExtension(param.getValue().getFile().getPath())));

        fileStatus_parsed.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getParsedStatus()));
        fileStatus_extracted.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getExtractedStatus()));

        millingTable.getColumns().setAll(fileName, fileLocation, fileExtension, fileStatus);
        millingTable.setItems(observableFiles);
    }

    private void initRightClick() {
        millingTable.setRowFactory(param -> {
            final TableRow<Filed> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();

            MenuItem showExtracted = new MenuItem("Show extracted");
            showExtracted.setOnAction(event -> showExtracted());

            MenuItem showParsed = new MenuItem("Show parsed");
            showParsed.setOnAction(event -> showParsed());

            rowMenu.getItems().addAll(showParsed, showExtracted);
            row.contextMenuProperty()
                    .bind(Bindings
                            .when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null));
            return row;
        });
    }

    public void addFiledToTable(Filed filed) {
        Platform.runLater(() -> {
            if (!observableFiles.contains(filed)) {
                observableFiles.add(filed);
            }
        });
    }
}
