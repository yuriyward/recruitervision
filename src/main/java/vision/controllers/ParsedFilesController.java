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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vision.models.Filed;
import vision.repository.FiledRepository;
import vision.service.GateService;
import vision.service.ScreensManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 03.08.2017.
 */
@FXMLController
public class ParsedFilesController implements Initializable {
    private final ScreensManager screensManager;
    private final static Logger logger = LoggerFactory.getLogger(ParsedFilesController.class);

    private final FiledRepository filedRepository;
    private final GateService gateService;

    @FXML
    private TableView<Filed> millingTable;

    @FXML
    private TableColumn<Filed, String> fileName;

    @FXML
    private TableColumn<Filed, String> fileLocation;

    @FXML
    private TableColumn<Filed, String> fileExtension;

    @FXML
    private TableColumn<Filed, String> language;

    @FXML
    private TableColumn fileStatus;

    @FXML
    private TableColumn<Filed, String> fileStatus_parsed;

    @FXML
    private TableColumn<Filed, String> fileStatus_extracted;

    private ObservableList<Filed> observableFiles = FXCollections.observableArrayList();


    @Autowired
    public ParsedFilesController(ScreensManager screensManager, FiledRepository filedRepository, GateService gateService) {
        this.screensManager = screensManager;
        this.filedRepository = filedRepository;
        this.gateService = gateService;
        initGate();
        addSubscription();
    }

    private void initGate() {
        gateService.initGate();
        gateService.initPlugins();
        gateService.initNewCorpus();
    }

    private void addSubscription() {
        filedRepository.getOnAdd().subscribe(this::addFiledToTable);
        filedRepository.getOnAdd().subscribe(this::addFileToCorpus);
        filedRepository.getOnRemove().subscribe(this::removeFileFromTable);
        filedRepository.getOnClear().subscribe(filed -> clearTable());
        filedRepository.getOnRefresh().subscribe(o -> refreshTable());
    }

    @FXML
    void backPageClick() {
        screensManager.showEndFileWindow();
    }

    @FXML
    void nextPageClick() {

    }

    @FXML
    void extract() {
        gateService.executeController();
        gateService.extractData();
    }

    @FXML
    private void showExtracted() {
        Filed filed = millingTable.getSelectionModel().getSelectedItem();
        if (filed != null) {
            logger.info("Extracted data for file " + filed.getFile().getName());
            logger.info(filed.getExtractedData().toString());
        } else {
            screensManager.showMaterialDialog("File not selected", "Please select file for showing", "OK");
        }
    }

    @FXML
    private void showParsed() {
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
        language.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLanguage()));

        fileStatus_parsed.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getParsedStatus()));
        fileStatus_extracted.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getExtractedStatus()));

        millingTable.getColumns().setAll(fileName, fileLocation, fileExtension, language, fileStatus);
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

    private void addFiledToTable(Filed filed) {
        Platform.runLater(() -> {
            if (!observableFiles.contains(filed)) {
                observableFiles.add(filed);
            }
        });
        logger.info("File added to Parsed files table");
    }

    private void addFileToCorpus(Filed filed) {
        if (filed.getParsedStatus().equals("OK"))
            Platform.runLater(() -> gateService.addFileToCorpus(filed));
    }

    private void removeFileFromTable(Filed filed) {
        if (filed != null) {
            observableFiles.remove(filed);
        }
        logger.info("File removed from Parsed files table");
    }

    private void clearTable() {
        observableFiles.clear();
    }

    private void refreshTable() { Platform.runLater(() -> millingTable.refresh());}
}
