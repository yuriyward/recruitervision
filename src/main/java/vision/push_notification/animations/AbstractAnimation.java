package vision.push_notification.animations;

import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import vision.push_notification.models.CustomStage;

/**
 * @author Yuriy on 12.10.2017.
 */
public abstract class AbstractAnimation implements Animation {
    protected final CustomStage stage;

    protected final Timeline showAnimation, dismissAnimation;
    protected final SequentialTransition sq;

    protected volatile boolean trayIsShowing;

    protected AbstractAnimation(CustomStage stage) {
        this.stage = stage;

        showAnimation = setupShowAnimation();
        dismissAnimation = setupDismissAnimation();

        sq = new SequentialTransition(setupShowAnimation(), setupDismissAnimation());
    }

    protected abstract Timeline setupShowAnimation();

    protected abstract Timeline setupDismissAnimation();

    @Override
    public final CustomStage getStage() {
        return stage;
    }

    @Override
    public final void playSequential(Duration dismissDelay) {
        sq.getChildren().get(1).setDelay(dismissDelay);
        sq.play();
    }

    @Override
    public final void playShowAnimation() {
        showAnimation.play();
    }

    @Override
    public final void playDismissAnimation() {
        dismissAnimation.play();
    }

    @Override
    public final boolean isShowing() {
        return trayIsShowing;
    }
}
