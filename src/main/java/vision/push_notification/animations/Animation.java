package vision.push_notification.animations;

import javafx.util.Duration;
import vision.push_notification.models.CustomStage;

/**
 * @author Yuriy on 12.10.2017.
 */
public interface Animation {
    void playSequential(Duration dismissDelay);

    void playShowAnimation();


    void playDismissAnimation();


    boolean isShowing();

    CustomStage getStage();
}
