package seedu.taskmanager.commons.util;

import javafx.scene.image.Image;
import seedu.taskmanager.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static Image getImage(String imagePath) {
        assert imagePath != null;
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

}
