package seedu.onetwodo.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import seedu.onetwodo.commons.core.ComponentManager;
import seedu.onetwodo.commons.core.Config;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.events.storage.DataSavingExceptionEvent;
import seedu.onetwodo.commons.events.ui.CloseDialogEvent;
import seedu.onetwodo.commons.events.ui.JumpToListRequestEvent;
import seedu.onetwodo.commons.events.ui.ShowHelpRequestEvent;
import seedu.onetwodo.commons.events.ui.ShowHelpUGRequestEvent;
import seedu.onetwodo.commons.events.ui.ShowTagsRequestEvent;
import seedu.onetwodo.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.onetwodo.commons.util.StringUtil;
import seedu.onetwodo.logic.Logic;
import seedu.onetwodo.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
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
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        // Set the application icon.
        // primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); // This should be called before creating other UI
            // parts
            mainWindow.fillInnerParts();

            mainWindow.showWelcomeDialog();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
        // Release resources when browser panel is loaded
        // mainWindow.releaseResources();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
            String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    // ==================== Event Handling Code ======================
    // ===============================================================

    @Subscribe
    private void handleCloseDialog(CloseDialogEvent event) {
        mainWindow.closeDialog();
    }

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    // @@author A0141138N
    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    //@@author A0135739W
    @Subscribe
    private void handleShowTagsEvent(ShowTagsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleTags(event.getTagsString());
    }

    // @@author A0141138N
    @Subscribe
    private void handleShowHelpUGEvent(ShowHelpUGRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelpUG();
    }

    // @@author A0143029M
    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        // Scroll when testing
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.taskType) {
        case DEADLINE:
            mainWindow.getDeadlineTaskListPanel().scrollTo(event.targetIndex);
            break;
        case EVENT:
            mainWindow.getEventTaskListPanel().scrollTo(event.targetIndex);
            break;
        case TODO:
            mainWindow.getTodoTaskListPanel().scrollTo(event.targetIndex);
            break;
        }
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.openDialog(event.getNewSelection());
    }

}
