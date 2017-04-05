package seedu.address.ui;

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

//@@author A0163848R
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

    /**
     *
     * @param Parent to set theme of.
     * @param Theme filename (without path or extension) to be applied.
     */
    public static void changeTheme(Parent root, String theme) {
        root.getStylesheets().clear();
        root.getStylesheets().add(MainApp.class.getResource(
                THEME_FILE_FOLDER
                + theme
                + STYLESHEET_EXTENSION)
                .toString());
    }

    private void setConnections(String path) {
        cssList.setItems(getThemes());
    }

    private void addToPlaceholder(AnchorPane placeholder) {
        SplitPane.setResizableWithParent(placeholder, false);
        placeholder.getChildren().add(cssList);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        cssList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        LOGGER.fine("Theme has changed to : '" + newValue + "'");
                        changeTheme(fxmlToApply, newValue);
                    }
                });
    }

    /**
     * @return Prewritten list of acceptable theme filenames (without path or extension).
     */
    private ObservableList<String> getThemes() {
        ObservableList<String> items = FXCollections.observableArrayList(
                "BlandTheme",
                "DarkTheme",
                "LimeTheme");
        return items;
    }
    
}
