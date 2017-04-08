package seedu.today.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.today.MainApp;
import seedu.today.commons.core.ComponentManager;
import seedu.today.commons.core.Config;
import seedu.today.commons.core.LogsCenter;
import seedu.today.commons.events.model.TaskManagerChangedEvent;
import seedu.today.commons.events.storage.DataSavingExceptionEvent;
import seedu.today.commons.events.ui.JumpToListRequestEvent;
import seedu.today.commons.events.ui.ShowHelpRequestEvent;
import seedu.today.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.today.commons.util.StringUtil;
import seedu.today.logic.Logic;
import seedu.today.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";
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
    public void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    public void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    // @@ Author: A0144315N
    /**
     * Scrolls the list to the position of the task to be highlighted
     */
    @Subscribe
    public void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetIndex.charAt(0) == 'T') {
            scrollToTodayTask(event);
        } else if (event.targetIndex.charAt(0) == 'F') {
            scrollToFutureTask(event);
        }
    }

    /**
     * Scrolls TodayTaskListView first to designated index followed by the outer
     * ScrollPane
     */
    private void scrollToTodayTask(JumpToListRequestEvent event) {
        int index = Integer.valueOf(event.targetIndex.substring(1)) - 1;
        ScrollPane scrollPane = mainWindow.getTaskListPanel().getScrollPane();
        TaskListPanel taskListPanel = mainWindow.getTaskListPanel();

        // scroll the internal ListView
        taskListPanel.scrollToToday(Integer.valueOf(index));

        // scroll the outside scroll pane
        // get combined height of today and future lists
        double totalHeight = taskListPanel.getTodayTaskListPanel().getHeight()
                + taskListPanel.getFutureTaskListPanel().getHeight() - scrollPane.getHeight();
        // calculate the current scroll position of the internal list
        double displayPosition = taskListPanel.rowHeight * index;
        double vValue = displayPosition / totalHeight > 1.0d ? 0.0d : displayPosition / totalHeight;
        scrollPane.setVvalue(vValue);

        logger.info("ScrollPane position: totalHeight: " + totalHeight + "; innerHeight:" + displayPosition
                + "; setVvalue:" + vValue);
    }

    /**
     * Scrolls FutureTaskListView first to designated index followed by the
     * outer ScrollPane
     */
    private void scrollToFutureTask(JumpToListRequestEvent event) {
        int index = Integer.valueOf(event.targetIndex.substring(1)) - 1;
        ScrollPane scrollPane = mainWindow.getTaskListPanel().getScrollPane();
        TaskListPanel taskListPanel = mainWindow.getTaskListPanel();

        // scroll the internal ListView
        taskListPanel.scrollToFuture(Integer.valueOf(index));

        // scroll the outside scroll pane
        // get combined height of today and future lists
        double totalHeight = taskListPanel.getTodayTaskListPanel().getHeight()
                + taskListPanel.getFutureTaskListPanel().getHeight() - scrollPane.getHeight();
        // calculate the current scroll position of the internal list
        double displayPosition = taskListPanel.getTodayTaskListPanel().getHeight() + taskListPanel.rowPadding
                + taskListPanel.rowHeight * index;
        double vValue = displayPosition / totalHeight > 1.0d ? 1.0d : displayPosition / totalHeight;
        scrollPane.setVvalue(vValue);

        logger.info("ScrollPane position: totalHeight: " + totalHeight + "; innerHeight:" + displayPosition
                + "; setVvalue:" + vValue);
    }

    @Subscribe
    public void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "###################### UI VIEW REFRESHED ###################"));
        mainWindow.prepareTaskList();
        mainWindow.getTaskListPanel().updateListHeight();
    }
}
