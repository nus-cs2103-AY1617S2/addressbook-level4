package seedu.address.ui;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser.ExtensionFilter;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.ExportRequestEvent;
import seedu.address.commons.events.ui.TargetFileRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

//@@author A0163848R
/**
 * The Theme Window. Provides a list of css files to load in order to change the application theme.
 * Under the list, a file browsing entry allows usage of an external css file.
 * The chosen configuration option is saved.
 */
public class ThemeWindow extends Window {

    public static final String DEFAULT_STYLESHEET = "LimeTheme";
    public static final String STYLESHEET_EXTENSION = ".css";
    public static final String THEME_FILE_FOLDER = "/themes/";
    
    protected static final String ICON = "/images/theme_icon.png";
    protected static final String FXML = "ThemeWindow.fxml";
    private static final String TITLE = "Theme Manager";

    @FXML
    private AnchorPane themeManagerPlaceholder;
    
    @FXML
    private Parent fxmlToApply;

    @FXML
    private ListView<String> themeListView;
    
    public ThemeWindow(Parent fxmlToApply) {
        super(FXML);

        setTitle(TITLE);
        FxViewUtil.setStageIcon(stage, ICON);
        this.fxmlToApply = fxmlToApply;

        setConnections(ThemeWindow.THEME_FILE_FOLDER);
        setEventHandlerForSelectionChangeEvent();
        setAccelerators();
    }
    
    private void setAccelerators() {
        themeListView.setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    handleExit();
                    keyEvent.consume();
                }
            }
           });
    }
    
    @FXML
    public void handleExit() {
        getStage().close();
    }
    
    /**
    *
    * @param Parent to set theme of.
    * @param Theme filename (without path or extension) to be applied.
    */
   public static void changeTheme(Parent root, String theme) {
       root.getStylesheets().clear();
       root.getStylesheets().add(MainApp.class.getResource(
               ThemeWindow.THEME_FILE_FOLDER
               + theme
               + ThemeWindow.STYLESHEET_EXTENSION)
               .toString());
   }

   private void setConnections(String path) {
       themeListView.setItems(getThemes());
   }

   private void setEventHandlerForSelectionChangeEvent() {
       themeListView.getSelectionModel().selectedItemProperty()
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
