package vision;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vision.own_components.SplashScreen;
import vision.utils.Props;
import vision.view.MainWindowView;

/**
 * @author Yuriy on 22.07.2017.
 */
@SpringBootApplication
public class Start extends AbstractJavaFxApplicationSupport {
    public static void main(String[] args) throws Exception {
        SplashScreen splashScreen = new SplashScreen();
        new Props();

        launchApp(Start.class, MainWindowView.class, splashScreen, args);

        getStage().setOnCloseRequest(event -> Platform.exit());
    }

}
