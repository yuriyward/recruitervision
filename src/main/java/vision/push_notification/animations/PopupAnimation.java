package vision.push_notification.animations;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import vision.push_notification.models.CustomStage;

/**
 * @author Yuriy on 12.10.2017.
 */
final class PopupAnimation extends AbstractAnimation {
    PopupAnimation(CustomStage stage) {
        super(stage);
    }

    @Override
    protected Timeline setupShowAnimation() {
        Timeline tl = new Timeline();

        KeyValue kv1 = new KeyValue(stage.yLocationProperty(), stage.getBottomRight().getY() + stage.getWidth());
        KeyFrame kf1 = new KeyFrame(Duration.ZERO, kv1);

        KeyValue kv2 = new KeyValue(stage.yLocationProperty(), stage.getBottomRight().getY());
        KeyFrame kf2 = new KeyFrame(Duration.millis(1000), kv2);

        KeyValue kv3 = new KeyValue(stage.opacityProperty(), 0.0);
        KeyFrame kf3 = new KeyFrame(Duration.ZERO, kv3);

        KeyValue kv4 = new KeyValue(stage.opacityProperty(), 1.0);
        KeyFrame kf4 = new KeyFrame(Duration.millis(2000), kv4);

        tl.getKeyFrames().addAll(kf1, kf2, kf3, kf4);

        tl.setOnFinished(e -> trayIsShowing = true);

        return tl;
    }

    @Override
    protected Timeline setupDismissAnimation() {
        Timeline tl = new Timeline();

        KeyValue kv1 = new KeyValue(stage.yLocationProperty(), stage.getY() + stage.getWidth());
        KeyFrame kf1 = new KeyFrame(Duration.millis(2000), kv1);

        KeyValue kv2 = new KeyValue(stage.opacityProperty(), 0.0);
        KeyFrame kf2 = new KeyFrame(Duration.millis(2000), kv2);

        tl.getKeyFrames().addAll(kf1, kf2);

        tl.setOnFinished(e -> {
            trayIsShowing = false;
            stage.close();
            stage.setLocation(stage.getBottomRight());
        });

        return tl;
    }
}
