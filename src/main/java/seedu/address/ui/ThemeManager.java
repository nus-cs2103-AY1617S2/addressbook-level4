package seedu.address.ui;

import java.io.IOException;
import java.nio.file.Paths;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.model.UserPrefs;

/**
 * The theme manager of the App.
 */
public class ThemeManager extends UiPart<Region> {

    public static final String THEME_FILE_FOLDER = "/themes/";
    public static final String STYLESHEET_EXTENSION = ".css";
    public static final String DEFAULT_STYLESHEET = "LimeTheme";
    
    private static final String FXML = "ThemeManager.fxml";

    @FXML
    private Parent fxmlToApply;

    private UserPrefs prefs;

    @FXML
    private ListView<String> cssList = new ListView<String>();

    /**
     * @param placeholder The AnchorPane where the ThemeManager must be inserted
     */
    public ThemeManager(AnchorPane placeholder, Parent fxmlToApply, UserPrefs prefs) {
        super(FXML);
        //setStyleSheet(DEFAULT_STYLESHEET);

        this.fxmlToApply = fxmlToApply;
        this.prefs = prefs;

        setConnections(THEME_FILE_FOLDER);
        addToPlaceholder(placeholder);
        setEventHandlerForSelectionChangeEvent();
    }

    public static void changeTheme(Parent root, String theme) {
        root.getStylesheets().clear();
        root.getStylesheets().add(MainApp.class.getResource(THEME_FILE_FOLDER + theme + STYLESHEET_EXTENSION).toString());
    }

    private void setConnections(String path) {
        cssList.setItems(getThemes(path));
    }

    private void addToPlaceholder(AnchorPane placeholder) {
        SplitPane.setResizableWithParent(placeholder, false);
        placeholder.getChildren().add(cssList);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        cssList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Theme has changed to : '" + newValue + "'");
                        changeTheme(fxmlToApply, newValue);
                        saveThemePreference(newValue, prefs);
                    }
                });
    }

    private ObservableList<String> getThemes(String path) {
        ObservableList<String> items = FXCollections.observableArrayList(
                "BlandTheme",
                "DarkTheme",
                "LimeTheme");
        /*
        try {
            for (String fileName : ResourceUtil.getResourceFiles(path)) {
                items.add(fileName);
            }
        } catch (IOException e) {
            logger.warning(e.toString());
        }
        */
        return items;
    }

    private void saveThemePreference(String theme, UserPrefs prefs) {
        prefs.getGuiSettings().setStyleSheet(theme);
    }
}
