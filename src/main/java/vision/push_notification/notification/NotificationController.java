package vision.push_notification.notification;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import vision.push_notification.animations.Animation;
import vision.push_notification.animations.Animations;
import vision.push_notification.models.CustomStage;

import java.io.IOException;
import java.net.URL;

/**
 * @author Yuriy on 12.10.2017.
 */
public final class NotificationController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private Rectangle rectangleColor;

    @FXML
    private ImageView imageIcon;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblMessage;

    @FXML
    private Label lblClose;

    private CustomStage stage;
    private Notification notification;
    private Animation animation;
    private EventHandler<ActionEvent> onDismissedCallBack, onShownCallback;


    public NotificationController(String title, String body, Image img,
                                  Paint rectangleFill, Notification notification) {
        initTrayNotification(title, body, notification);

        setImage(img);
        setRectangleFill(rectangleFill);
    }

    public NotificationController(String title, String body, Notification notification) {
        initTrayNotification(title, body, notification);
    }

    public NotificationController(Notification notification) {
        this("", "", notification);
    }

    public NotificationController() {
        this(Notifications.NOTICE);
    }

    private void initTrayNotification(String title, String message, Notification type) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/notification.fxml"));

        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        initStage();
        initAnimations();

        setTray(title, message, type);
    }

    private void initAnimations() {
        setAnimation(Animations.SLIDE); // Default animation type
    }

    private void initStage() {
        stage = new CustomStage(rootNode, StageStyle.UNDECORATED);
        stage.setScene(new Scene(rootNode));
        stage.setAlwaysOnTop(true);
        stage.setLocation(stage.getBottomRight());

        lblClose.setOnMouseClicked(e -> dismiss());
    }

    public void setNotification(Notification nType) {
        notification = nType;

        URL imageLocation = getClass().getClassLoader().getResource(nType.getURLResource());
        setRectangleFill(Paint.valueOf(nType.getPaintHex()));
        setImage(new Image(imageLocation.toString()));
        setTrayIcon(imageIcon.getImage());
    }

    public Notification getNotification() {
        return notification;
    }

    public void setTray(String title, String message, Notification type) {
        setTitle(title);
        setMessage(message);
        setNotification(type);
    }

    public void setTray(String title, String message, Image img, Paint rectangleFill, Animation animation) {
        setTitle(title);
        setMessage(message);
        setImage(img);
        setRectangleFill(rectangleFill);
        setAnimation(animation);
    }

    public boolean isTrayShowing() {
        return animation.isShowing();
    }

    public void showAndDismiss(Duration dismissDelay) {
        if (!isTrayShowing()) {
            stage.show();

            onShown();
            animation.playSequential(dismissDelay);
        } else dismiss();

        onDismissed();
    }

    public void showAndWait() {
        if (!isTrayShowing()) {
            stage.show();

            animation.playShowAnimation();

            onShown();
        }
    }

    public void dismiss() {
        if (isTrayShowing()) {
            animation.playDismissAnimation();
            onDismissed();
        }
    }

    private void onShown() {
        if (onShownCallback != null)
            onShownCallback.handle(new ActionEvent());
    }

    private void onDismissed() {
        if (onDismissedCallBack != null)
            onDismissedCallBack.handle(new ActionEvent());
    }

    public void setOnShown(EventHandler<ActionEvent> event) {
        onShownCallback = event;
    }

    public void setTrayIcon(Image img) {
        stage.getIcons().clear();
        stage.getIcons().add(img);
    }

    public Image getTrayIcon() {
        return stage.getIcons().get(0);
    }

    public void setTitle(String txt) {
        Platform.runLater(() -> lblTitle.setText(txt));
    }

    public String getTitle() {
        return lblTitle.getText();
    }

    public void setMessage(String txt) {
        lblMessage.setText(txt);
    }

    public String getMessage() {
        return lblMessage.getText();
    }

    public void setImage(Image img) {
        imageIcon.setImage(img);

        setTrayIcon(img);
    }

    public Image getImage() {
        return imageIcon.getImage();
    }

    public void setRectangleFill(Paint value) {
        rectangleColor.setFill(value);
    }

    public Paint getRectangleFill() {
        return rectangleColor.getFill();
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void setAnimation(Animations animation) {
        setAnimation(animation.newInstance(stage));
    }

    public Animation getAnimation() {
        return animation;
    }
}
