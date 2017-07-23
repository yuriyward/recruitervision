package vision.controllers;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import vision.service.ActionService;

/**
 * @author Yuriy on 22.07.2017.
 */
@FXMLController
public class MainWindowController {
    @FXML
    private Label helloLabel;
    @FXML
    private TextField nameField;

    @Autowired
    private ActionService actionService;

    @FXML
    private void buttonClick() {
        final String textToBeShown = actionService.processName(nameField.getText());
        helloLabel.setText(textToBeShown);
    }
}
