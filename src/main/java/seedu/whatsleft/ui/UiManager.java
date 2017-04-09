package seedu.whatsleft.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.whatsleft.MainApp;
import seedu.whatsleft.commons.core.ComponentManager;
import seedu.whatsleft.commons.core.Config;
import seedu.whatsleft.commons.core.LogsCenter;
import seedu.whatsleft.commons.events.model.WhatsLeftChangedEvent;
import seedu.whatsleft.commons.events.storage.DataSavingExceptionEvent;
import seedu.whatsleft.commons.events.ui.JumpToCalendarEventEvent;
import seedu.whatsleft.commons.events.ui.JumpToCalendarTaskEvent;
import seedu.whatsleft.commons.events.ui.JumpToEventListRequestEvent;
import seedu.whatsleft.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.whatsleft.commons.events.ui.ShowHelpRequestEvent;
import seedu.whatsleft.commons.events.ui.UpdateCalendarEvent;
import seedu.whatsleft.commons.exceptions.CalendarUnsyncException;
import seedu.whatsleft.commons.util.StringUtil;
import seedu.whatsleft.logic.Logic;
import seedu.whatsleft.model.UserPrefs;

//@@author A0148038A
/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/WhatsLeft.png";
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
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); // This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
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

    //@@author A0124377A
    // ==================== Event Handling Code
    // ===============================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    // @@author A0110491U
    @Subscribe
    private void handleJumpToEventListRequestEvent(JumpToEventListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getEventListPanel().scrollTo(event.targetIndex);
    }

    // @@author A0124377A
    @Subscribe
    private void handleJumpToCalendarEventEvent(JumpToCalendarEventEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        try {
            mainWindow.getCalendarPanel().selection(event.targetEvent);
        } catch (CalendarUnsyncException e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error unsycn calendar:", e);
        }
    }

    // @@author A0110491U
    @Subscribe
    private void handleJumpToTaskListRequestEvent(JumpToTaskListRequestEvent task) {
        logger.info(LogsCenter.getEventHandlingLogMessage(task));
        mainWindow.getTaskListPanel().scrollTo(task.targetIndex);
    }

    // @@author A0124377A
    @Subscribe
    private void handleJumpToCalendarTaskEvent(JumpToCalendarTaskEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.canJumpTo()) {
            try {
                mainWindow.getCalendarPanel().selection(event.targetTask);
            } catch (CalendarUnsyncException e) {
                logger.severe(StringUtil.getDetails(e));
                showFatalErrorDialogAndShutdown("Fatal error unsycn calendar:", e);
            }
        }
    }

    // @@author A0124377A
    @Subscribe
    private void handleEventListUpdatedEvent(WhatsLeftChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.updateCalendar(event.data.getEventList(), event.data.getTaskList());
    }

    // @@author A0124377A
    @Subscribe
    private void handleUpdateCalendarEvent(UpdateCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.changeCalendarView(event.getNextDateTime());
    }

}
