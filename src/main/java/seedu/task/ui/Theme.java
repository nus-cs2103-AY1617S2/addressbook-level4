package seedu.task.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import seedu.task.MainApp;

//@@author A0163848R
/**
 * Class for managing available themes and theme-related methods
 */
public class Theme {

    public static final String STYLESHEET_EXTENSION = ".css";
    public static final String THEME_FILE_FOLDER = "/themes/";
    public static final String EXTENSIONS_STYLESHEET = "/view/Extensions.css";

    public static ObservableList<String> THEMES = FXCollections.observableArrayList(
            "BlandTheme",
            "DarkTheme",
            "BurntCyanTheme",
            "BurntCopperTheme");
    public static final String DEFAULT_STYLESHEET = THEMES.get(2); // Burnt Cyan Theme

    /**
     *
     * @param Parent to set theme of.
     * @param Theme filename (without path or extension) to be applied.
     */
    public static void changeTheme(Parent root, String theme) {

        root.getStylesheets().clear();

        root.getStylesheets().add(MainApp.class.getResource(
               Theme.THEME_FILE_FOLDER
               + theme
               + Theme.STYLESHEET_EXTENSION)
               .toString());

        root.getStylesheets().add(MainApp.class.getResource(Theme.EXTENSIONS_STYLESHEET).toString());
    }
}
