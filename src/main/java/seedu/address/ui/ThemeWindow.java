package seedu.address.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import seedu.address.commons.util.FxViewUtil;

//@@author A0163848R
/**
 * The Theme Window. Provides a list of css files to load in order to change the application theme.
 * Under the list, a file browsing entry allows usage of an external css file.
 * The chosen configuration option is saved.
 */
public class ThemeWindow extends Window {

    protected static final String ICON = "/images/theme_icon.png";
    protected static final String FXML = "ThemeWindow.fxml";
    private static final String TITLE = "Theme Manager";

    @FXML
    private AnchorPane themeManagerPlaceholder;

    @FXML
    private Parent fxmlToApply;

    @FXML
    public ListView<String> themeListView;

    public ThemeWindow(Parent fxmlToApply) {
        super(FXML);

        setTitle(TITLE);
        FxViewUtil.setStageIcon(stage, ICON);
        this.fxmlToApply = fxmlToApply;

        setConnections(Theme.THEME_FILE_FOLDER);
        setEventHandlerForSelectionChangeEvent();
        setAccelerators();
    }

    private void setAccelerators() {
        themeListView.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
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

    private void setConnections(String path) {
        themeListView.setItems(Theme.THEMES);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        themeListView.getSelectionModel().selectedItemProperty()
               .addListener((observable, oldValue, newValue) -> {
                   if (newValue != null) {
                       LOGGER.fine("Theme has changed to : '" + newValue + "'");
                       Theme.changeTheme(fxmlToApply, newValue);
                   }
               });
    }
}
