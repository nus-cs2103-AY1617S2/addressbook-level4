package seedu.ezdo.ui;

import java.io.InputStream;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.ezdo.MainApp;
import seedu.ezdo.commons.core.ComponentManager;
import seedu.ezdo.commons.core.Config;
import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.commons.events.storage.DataSavingExceptionEvent;
import seedu.ezdo.commons.events.ui.JumpToListRequestEvent;
import seedu.ezdo.commons.events.ui.ShowHelpRequestEvent;
import seedu.ezdo.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.ezdo.commons.util.StringUtil;
import seedu.ezdo.logic.Logic;
import seedu.ezdo.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {

    private static final String MESSAGE_SAVE_FILE_ERROR = "Could not save data to file";
    private static final String MESSAGE_SAVE_ERROR = "Could not save data";
    private static final String MESSAGE_SPACE = " ";
    private static final String NEW_LINE = ":\n";
    private static final String MESSAGE_INITIALISE_ERROR = "Fatal error during initializing";
    private static final String MESSAGE_FILE_OP_ERROR = "File Op Error";
    private static final String LINK_TO_CSS = "view/DarkTheme.css";
    private static final String MESSAGE_UI_STARTING = "Starting UI...";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/ezDo_32.png";
    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info(MESSAGE_UI_STARTING);
        
        // Set the application title
        String appTitle = config.getAppTitle();
        primaryStage.setTitle(appTitle);

        // Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown(MESSAGE_INITIALISE_ERROR, e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + NEW_LINE + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, MESSAGE_FILE_OP_ERROR, description, content);
    }

    private Image getImage(String imagePath) {
        InputStream imageStream = MainApp.class.getResourceAsStream(imagePath);
        return new Image(imageStream);
    }

    private void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add(LINK_TO_CSS);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + MESSAGE_SPACE + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code ===============================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait(MESSAGE_SAVE_ERROR, MESSAGE_SAVE_FILE_ERROR, event.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        int index = event.targetIndex;
        mainWindow.getTaskListPanel().scrollTo(index);
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

}
