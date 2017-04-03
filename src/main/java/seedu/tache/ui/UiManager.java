package seedu.tache.ui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

//import org.controlsfx.control.Notifications;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import seedu.tache.commons.events.storage.DataSavingExceptionEvent;
import seedu.tache.commons.events.ui.JumpToListRequestEvent;
import seedu.tache.commons.events.ui.ShowHelpRequestEvent;
import seedu.tache.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.tache.commons.util.StringUtil;
import seedu.tache.logic.Logic;
import seedu.tache.model.UserPrefs;
import seedu.tache.model.task.ReadOnlyTask;

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
    private MainWindow mainWindow;
    private Timer notificationTimer;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
        this.notificationTimer = new Timer();
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
        initTasksWithNotificationTimer(logic.getFilteredTaskList());
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
        mainWindow.releaseResources();
        hotkeyManager.stop();
        notificationTimer.cancel();
    }

    //@@author A0139961U
    /**
     * Sets a notification timer to tasks that are due tomorrow. The notification timer
     * will then call showSystemTrayNotification method.
     * @param taskList: Lists of tasks from user's data storage file.
     */
    private void initTasksWithNotificationTimer(ObservableList<ReadOnlyTask> taskList) {
        Date tomorrow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tomorrow);
        calendar.add(Calendar.DATE, 1);
        tomorrow = calendar.getTime();
        for (ReadOnlyTask task : taskList) {
            if (task.getEndDateTime().isPresent()) {
                if (task.getEndDateTime().get().isSameDate(tomorrow)) {
                    notificationTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                showSystemTrayNotification(task);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (AWTException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 0); //0 indicate that it will only be scheduled once
                }
            }
        }
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

    //@@author A0139961U
    /**
     * Shows a notification from the javafx UI
     * @param task: The task that is being notified about.
     */
    /*private void showUpdateNotification(ReadOnlyTask task) {
        ImageView icon = new ImageView(this.getClass().getResource("/images/info_icon.png").toString());
        icon.setFitWidth(64);
        icon.setFitHeight(64);
        Notifications.create()
           .title(task.getName().fullName + " is due tomorrow.")
           .text("This task is due tomorrow.")
           .graphic(icon)
           .position(Pos.BOTTOM_RIGHT)
           .hideAfter(Duration.seconds(5))
           .owner(mainWindow.getPrimaryStage())
           .darkStyle()
            .show();
    }*/

    //@@author A0139961U
    /**
     * Shows a notification from the system tray
     * @param task: The task that is being notified about.
     */
    private void showSystemTrayNotification(ReadOnlyTask task) throws AWTException, java.net.MalformedURLException {
        String displayMsg = "This task is due tomorrow";
        if (!task.getEndDateTime().get().getTimeOnly().isEmpty()) {
            String time = task.getEndDateTime().get().getTimeOnly();
            displayMsg += " at " + time.substring(0, time.length() - 3) + ".";
        } else {
            displayMsg += ".";
        }

        SystemTray tray = SystemTray.getSystemTray();
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/info_icon.png"));
        java.awt.Image image = icon.getImage();
        TrayIcon trayIcon = new TrayIcon(image, "notification");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip(task.getName().fullName + " is due tomorrow.");

        MenuItem dismissMenuItem = new MenuItem("Dismiss");
        dismissMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
            }
        });

        PopupMenu popupMenu = new PopupMenu();
        popupMenu.add(dismissMenuItem);

        trayIcon.setPopupMenu(popupMenu);
        tray.add(trayIcon);
        trayIcon.displayMessage(task.getName().fullName, displayMsg, MessageType.INFO);
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

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.viewTaskEvent(event.getNewSelection());
    }

}
