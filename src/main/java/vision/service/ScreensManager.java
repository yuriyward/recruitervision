package vision.service;

import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vision.Start;
import vision.controllers.MainWindowController;
import vision.view.CvFilesView;
import vision.view.DataForExtractionView;
import vision.view.EndFileView;

/**
 * @author Yuriy on 22.07.2017.
 */
@Component
public class ScreensManager {
    private MainWindowController mainWindowController;

    @Autowired
    CvFilesView cvFilesView;

    @Autowired
    DataForExtractionView dataForExtractionView;

    @Autowired
    EndFileView endFileView;

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

    public void showEndFileWindow(){
        mainWindowController.getAnchorPane().getChildren().clear();
        mainWindowController.getAnchorPane().getChildren().add(endFileView.getView());
    }

}
