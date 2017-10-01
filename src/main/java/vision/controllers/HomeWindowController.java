package vision.controllers;

import com.jfoenix.controls.JFXCheckBox;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import vision.service.ScreensManager;
import vision.utils.Props;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yuriy on 29.07.2017.
 */
@FXMLController
public class HomeWindowController implements Initializable {
    private final Props props;
    private final ScreensManager screensManager;

    @FXML
    private JFXCheckBox PARSE_FILE_BY_TIKA;

    @Autowired
    public HomeWindowController(ScreensManager screensManager, Props props) {
        this.screensManager = screensManager;
        this.props = props;
    }

    @FXML
    void nextPageClick() {
        screensManager.showCvFilesWindow();
    }

    @FXML
    void parseFileByTikaChk() {
        props.saveToPropertiesFile("PARSE_FILE_BY_TIKA", String.valueOf(PARSE_FILE_BY_TIKA.isSelected()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectDefaults();
    }

    private void selectDefaults() {
        PARSE_FILE_BY_TIKA.setSelected(props.isPARSE_FILE_BY_TIKA());
    }
}
