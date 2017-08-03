package vision.service;

import javafx.application.Platform;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vision.Start;
import vision.controllers.MainWindowController;
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

//    @Autowired
//    ExploreDataView exploreDataView;

    @Autowired
    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void showCvFilesWindow() {
        mainWindowController.getAnchorPane().getChildren().clear();
        mainWindowController.getAnchorPane().getChildren().add(cvFilesView.getView());
    }

    public void showDataForExtractionWindow() {
        mainWindowController.getAnchorPane().getChildren().clear();
        mainWindowController.getAnchorPane().getChildren().add(dataForExtractionView.getView());
    }

    public void showEndFileWindow() {
        mainWindowController.getAnchorPane().getChildren().clear();
        mainWindowController.getAnchorPane().getChildren().add(endFileView.getView());
    }

    public void showHomeWindow() {
        mainWindowController.getAnchorPane().getChildren().clear();
        mainWindowController.getAnchorPane().getChildren().add(homeView.getView());
    }

    public void showAdvancedSelectionWindow() {
        mainWindowController.getAnchorPane().getChildren().clear();
        mainWindowController.getAnchorPane().getChildren().add(advancedSelectionView.getView());
    }

    public void showParesedFiles() {
        mainWindowController.getAnchorPane().getChildren().clear();
        mainWindowController.getAnchorPane().getChildren().add(parsedFilesView.getView());
    }

    public void showExploreDataWindow() {
//        mainWindowController.getAnchorPane().getChildren().clear();
        Platform.runLater(() -> {
                Start.showView(ExploreDataView.class);
//            mainWindowController.getAnchorPane().getChildren().add(exploreDataView.getView());
        });
    }

}
