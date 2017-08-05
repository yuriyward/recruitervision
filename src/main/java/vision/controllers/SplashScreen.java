package vision.controllers;

/**
 * @author Yuriy on 22.07.2017.
 */
public class SplashScreen extends de.felixroske.jfxsupport.SplashScreen {
    private static String APPLICATION_ICON =
            "/images/splash.png";

    @Override
    public String getImagePath() {
        return APPLICATION_ICON;
    }
}
