package seedu.today.commons.util;

import java.net.URISyntaxException;

import javafx.scene.image.Image;
import seedu.today.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static Image getImage(String imagePath) {
        assert imagePath != null;
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    public static String getHelpPageLocation(String helpPagePath) throws URISyntaxException {
        assert helpPagePath != null;
        return MainApp.class.getResource(helpPagePath).toURI().toString();
    }

}
