package vision;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vision.controllers.SplashScreen;
import vision.view.MainWindowView;
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
