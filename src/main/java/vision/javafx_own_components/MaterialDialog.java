package vision.javafx_own_components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lombok.AllArgsConstructor;

/**
 * @author Yuriy on 05.08.2017.
 */
@AllArgsConstructor
public class MaterialDialog {
    private String header;
    private String content;
    private String buttonLabel;
    private StackPane stackPane;

    public void showDialog() {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text(header));
        jfxDialogLayout.setBody(new Text(content));
        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton okey = new JFXButton(buttonLabel);
        okey.setPrefWidth(110);
        okey.setStyle("-fx-background-color: #F39C12; -fx-text-fill: white;");
        okey.setButtonType(JFXButton.ButtonType.RAISED);
        okey.setOnAction(event -> {
            jfxDialog.close();
            stackPane.setVisible(false);
        });
        stackPane.setOnMouseClicked(event -> stackPane.setVisible(false));
        jfxDialogLayout.setActions(okey);
        jfxDialog.show();
    }
}
