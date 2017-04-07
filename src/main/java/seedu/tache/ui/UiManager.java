package seedu.tache.ui;

import java.util.logging.Logger;

//import org.controlsfx.control.Notifications;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
//import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.stage.Stage;
//import javafx.util.Duration;
import seedu.tache.MainApp;
import seedu.tache.commons.core.ComponentManager;
import seedu.tache.commons.core.Config;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.events.model.TaskManagerChangedEvent;
import seedu.tache.commons.events.storage.DataSavingExceptionEvent;
import seedu.tache.commons.events.ui.JumpToListRequestEvent;
import seedu.tache.commons.events.ui.PopulateRecurringGhostTaskEvent;
import seedu.tache.commons.events.ui.ShowHelpRequestEvent;
import seedu.tache.commons.events.ui.TaskPanelConnectionChangedEvent;
import seedu.tache.commons.util.StringUtil;
import seedu.tache.logic.Logic;
import seedu.tache.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/tache.png";
    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";
    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private HotkeyManager hotkeyManager;
    private NotificationManager notificationManager;
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

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        hotkeyManager = new HotkeyManager(primaryStage);
        hotkeyManager.start();

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }

        notificationManager = new NotificationManager(logic);
        notificationManager.start();
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
        mainWindow.releaseResources();
        hotkeyManager.stop();
        notificationManager.stop();
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
        alert.getDialogPane().getStylesheets().add("view/TacheTheme.css");
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

    //==================== Event Handling Code ===============================================================

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

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().scrollTo(event.targetIndex);
    }

    //@@author A0139925U
    @Subscribe
    private void handleTaskPanelConnectionChangedEvent(TaskPanelConnectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (mainWindow.getTaskListPanel() != null) {
            mainWindow.getTaskListPanel().resetConnections(event.getNewConnection());
        }
    }

    @Subscribe
    private void handlePopulateRecurringGhostTaskEvent(PopulateRecurringGhostTaskEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (mainWindow.getTaskListPanel() != null) {
            mainWindow.getCalendarPanel().addAllEvents(event.getAllRecurringGhostTasks());;
        }
    }

    @Subscribe
    public void handleUpdateNotificationsEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        notificationManager.updateNotifications(event);
    }

}
