package vision.push_notification.animations;

import vision.push_notification.models.CustomStage;

import java.util.function.Function;

/**
 * @author Yuriy on 12.10.2017.
 */
public enum  Animations {
    SLIDE(SlideAnimation::new),
    FADE(FadeAnimation::new),
    POPUP(PopupAnimation::new);

    private final Function<CustomStage, Animation> newInstance;

    Animations(Function<CustomStage, Animation> newInstance) {
        this.newInstance = newInstance;
    }

    public Animation newInstance(CustomStage stage) {
        return newInstance.apply(stage);
    }
}
