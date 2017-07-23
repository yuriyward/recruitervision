package vision;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vision.view.MainWindowView;
import vision.controllers.SplashScreen;
/**
 * @author Yuriy on 22.07.2017.
 */
@SpringBootApplication
public class Start extends AbstractJavaFxApplicationSupport {
    public static void main(String[] args) throws Exception {
        SplashScreen splashScreen = new SplashScreen();
        launchApp(Start.class, MainWindowView.class, splashScreen, args);
    }

}
