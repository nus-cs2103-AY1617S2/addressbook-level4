package seedu.taskmanager.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.taskmanager.MainApp;
import seedu.taskmanager.commons.core.ComponentManager;
import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.events.model.TaskManagerChangedEvent;
import seedu.taskmanager.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskmanager.commons.events.ui.JumpToListRequestEvent;
import seedu.taskmanager.commons.events.ui.ShowHelpRequestEvent;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.logic.Logic;
import seedu.taskmanager.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/task_manager.png";
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
            mainWindow.show(); // This should be called before creating other UI
                               // parts
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

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        if (mainWindow.getEventTaskListPanel().getEventTaskListView().getItems().size()
                > event.targetIndex) {
            mainWindow.getEventTaskListPanel().scrollTo(event.targetIndex);
            mainWindow.getDeadlineTaskListPanel().getDeadlineTaskListView().getSelectionModel().clearSelection();
            mainWindow.getFloatingTaskListPanel().getFloatingTaskListView().getSelectionModel().clearSelection();
        } else {
            if ((mainWindow.getEventTaskListPanel().getEventTaskListView().getItems().size() + mainWindow
                    .getDeadlineTaskListPanel().getDeadlineTaskListView().getItems().size()) > event.targetIndex) {
                mainWindow.getDeadlineTaskListPanel().scrollTo(event.targetIndex
                        - mainWindow.getEventTaskListPanel().getEventTaskListView().getItems().size());
                mainWindow.getEventTaskListPanel().getEventTaskListView().getSelectionModel().clearSelection();
                mainWindow.getFloatingTaskListPanel().getFloatingTaskListView().getSelectionModel().clearSelection();
            } else {
                mainWindow.getFloatingTaskListPanel().scrollTo(event.targetIndex
                        - ((mainWindow.getEventTaskListPanel().getEventTaskListView().getItems().size()
                                + mainWindow.getDeadlineTaskListPanel().getDeadlineTaskListView().getItems().size())));
                mainWindow.getEventTaskListPanel().getEventTaskListView().getSelectionModel().clearSelection();
                mainWindow.getDeadlineTaskListPanel().getDeadlineTaskListView().getSelectionModel().clearSelection();
            }
        }
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce) {
        mainWindow.getEventTaskListPanel().setConnections(logic.getFilteredTaskList());
        mainWindow.getDeadlineTaskListPanel().setConnections(logic.getFilteredTaskList());
        mainWindow.getFloatingTaskListPanel().setConnections(logic.getFilteredTaskList());
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Updating Task List Panels"
                + Integer.toString(mainWindow.getEventTaskListPanel().getEventTaskListView().getItems().size())));
    }
}
