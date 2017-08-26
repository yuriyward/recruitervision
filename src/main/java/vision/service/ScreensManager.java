package vision.service;

import com.jfoenix.controls.JFXButton;
import javafx.stage.Modality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import vision.Start;
import vision.controllers.ExploreDataController;
import vision.controllers.MainWindowController;
import vision.javafx_own_components.MaterialDialog;
import vision.models.Filed;
import vision.view.*;

/**
 * @author Yuriy on 22.07.2017.
 */
@Component
public class ScreensManager {
    private MainWindowController mainWindowController;

    @Autowired
    HomeView homeView;

    @Autowired
    CvFilesView cvFilesView;

    @Autowired
    DataForExtractionView dataForExtractionView;

    @Autowired
    EndFileView endFileView;

    @Autowired
    AdvancedSelectionView advancedSelectionView;

    @Autowired
    ParsedFilesView parsedFilesView;

    @Autowired
    ExploreDataView exploreDataView;
    @Autowired
    ExploreDataController exploreDataController;
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void showCvFilesWindow() {
        mainWindowController.getAnchorPane().getChildren().clear();
        handleSelection(mainWindowController.getFilesBtn());
        mainWindowController.getAnchorPane().getChildren().add(cvFilesView.getView());
    }

    public void showDataForExtractionWindow() {
        mainWindowController.getAnchorPane().getChildren().clear();
        handleSelection(mainWindowController.getInformationBtn());
        mainWindowController.getAnchorPane().getChildren().add(dataForExtractionView.getView());
    }

    public void showEndFileWindow() {
        mainWindowController.getAnchorPane().getChildren().clear();
        handleSelection(mainWindowController.getEndFileBtn());
        mainWindowController.getAnchorPane().getChildren().add(endFileView.getView());
    }

    public void showHomeWindow() {
        mainWindowController.getAnchorPane().getChildren().clear();
        handleSelection(mainWindowController.getHomeBtn());
        mainWindowController.getAnchorPane().getChildren().add(homeView.getView());
    }

    public void showAdvancedSelectionWindow() {
        mainWindowController.getAnchorPane().getChildren().clear();
        handleSelection(mainWindowController.getAdvancedSelectionId());
        mainWindowController.getAnchorPane().getChildren().add(advancedSelectionView.getView());
    }

    public void showParesedFiles() {
        mainWindowController.getAnchorPane().getChildren().clear();
        handleSelection(mainWindowController.getShowParsedDataBtn());
        mainWindowController.getAnchorPane().getChildren().add(parsedFilesView.getView());
    }

    public void showExploreDataWindow(Filed filed) {
        exploreDataController.setFiled(filed);
        Start.showView(ExploreDataView.class, Modality.APPLICATION_MODAL);
    }

    public void showMaterialDialog(String header, String content, String buttonLabel) {
        mainWindowController.getStackPane().setVisible(true);
        MaterialDialog materialDialog = new MaterialDialog(header, content, buttonLabel, mainWindowController.getStackPane());
        materialDialog.showDialog();
    }

    private void handleSelection(JFXButton selectedBtn) {
        selectedBtn.setStyle("-fx-background-color: #6aae31;-fx-font-size: 16;-fx-font-weight:bold;-fx-text-fill:white");
        mainWindowController.unmarkNotSelectedButtons(selectedBtn);
    }
}
