package vision.javafx_own_components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Yuriy on 05.08.2017.
 */
public class MaterialDialog {
    private String header;
    private String content;
    private String buttonLabel;
    private String directoryLocation;
    private StackPane stackPane;

    public MaterialDialog(String header, String content, String buttonLabel, StackPane stackPane) {
        this.header = header;
        this.content = content;
        this.buttonLabel = buttonLabel;
        this.stackPane = stackPane;
    }

    public MaterialDialog(String directoryLocation, StackPane stackPane) {
        this.stackPane = stackPane;
        this.directoryLocation = directoryLocation;
    }

    public void showDialog() {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text(header));
        jfxDialogLayout.setBody(new Text(content));
        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton okay = new JFXButton(buttonLabel);
        okay.setPrefWidth(110);
        okay.setStyle("-fx-background-color: #F39C12; -fx-text-fill: white;");
        okay.setButtonType(JFXButton.ButtonType.RAISED);
        okay.setOnAction(event -> {
            jfxDialog.close();
            stackPane.setVisible(false);
        });
        stackPane.setOnMouseClicked(event -> stackPane.setVisible(false));
        jfxDialogLayout.setActions(okay);
        jfxDialog.show();
    }

    public void showDialogWithDirectory() {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text("Candidates list created"));
        jfxDialogLayout.setBody(new Text("Candidates list created, now you can open file at this directory [" + directoryLocation + "]"));
        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton okay = new JFXButton("Thanks");
        JFXButton openDirectory = new JFXButton("Open directory");
        okay.setPrefWidth(110);
        okay.setStyle("-fx-background-color: #F39C12; -fx-text-fill: white;");
        okay.setButtonType(JFXButton.ButtonType.RAISED);
        okay.setOnAction(event -> {
            jfxDialog.close();
            stackPane.setVisible(false);
        });

        openDirectory.setPrefWidth(110);
        openDirectory.setStyle("-fx-background-color:  #00A65A; -fx-text-fill: white;");
        openDirectory.setButtonType(JFXButton.ButtonType.RAISED);
        openDirectory.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(directoryLocation));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stackPane.setVisible(false);
        });

        stackPane.setOnMouseClicked(event -> stackPane.setVisible(false));
        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(openDirectory, okay);
        jfxDialogLayout.setActions(hBox);
        jfxDialog.show();
    }
}
