//@@author A0139961U
package seedu.tache.ui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import javafx.collections.ObservableList;
import seedu.tache.commons.events.model.TaskManagerChangedEvent;
import seedu.tache.logic.Logic;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Contains methods related to system tray notifications.
 *
 */
public class NotificationManager {

    public static final int EVENT_TYPE = 0;
    public static final int DEADLINE_TYPE = 1;
    public static final int REMOVE_SECONDS_OFFSET = 3;
    public static final String IMAGE_ICON_PATH = "/images/info_icon.png";
    public static final String NOTIFICATION_TEXT = "notification";
    public static final String DISMISS_TEXT = "Dismiss";
    public static final String DUE_TIME_WITH_NAME_TEXT = " is due in 2Hrs.";
    public static final String DUE_TIME_WITHOUT_NAME_TEXT = "This task is due in 2Hrs";
    public static final String START_TIME_WITH_NAME_TEXT = " is starting in 2Hrs.";
    public static final String START_TIME_WITHOUT_NAME_TEXT = "This task is starting in 2Hrs";
    public static final String FULL_STOP_TEXT = ".";
    public static final String AT_TEXT = " at ";

    private Logic logic;
    private Timer notificationTimer;

    public NotificationManager(Logic logic) {
        this.logic = logic;
        notificationTimer = new Timer();
    }

    /**
     * Sets a notification timer to tasks that are due tomorrow. The notification timer.
     * will then call showSystemTrayNotification method.
     * @param taskList: Lists of tasks from user's data storage file.
     */
    private void initNotificationTimerWithTasks(ObservableList<ReadOnlyTask> taskList) {
        for (ReadOnlyTask task : taskList) {
            if (task.getStartDateTime().isPresent() && isDueInMoreThanTwoHours(task.getStartDateTime().get())) {
                notificationTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        showSystemTrayNotification(task, EVENT_TYPE);
                    }
                }, getTwoHoursBefore(task.getStartDateTime().get())); //0 indicate that it will only be scheduled once
            } else if (task.getEndDateTime().isPresent() && isDueInMoreThanTwoHours(task.getEndDateTime().get())) {
                notificationTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        showSystemTrayNotification(task, DEADLINE_TYPE);
                    }
                }, getTwoHoursBefore(task.getEndDateTime().get())); //0 indicate that it will only be scheduled once
            }
        }
    }

    /**
     * Converts the time of the given object to 2 hours before it with a 3 seconds
     * offset (1hour 59minutes and 57 seconds).
     * @param dateTime: The object to modify the time to 2 hours before.
     * @return a Date object which is 2 hours before the parsed in DateTime object's time.
     */
    private Date getTwoHoursBefore(DateTime dateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime.getDate());
        cal.add(Calendar.HOUR, -1); // -1 hour from the given time
        cal.add(Calendar.MINUTE, -59); // -59 minutes from the given time
        cal.add(Calendar.SECOND, -57); // minus 57 seconds from the given time
        return cal.getTime();
    }

    private boolean isDueInMoreThanTwoHours(DateTime dateTime) {
        Date now = new Date();
        if (getTwoHoursBefore(dateTime).before(now)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Shows a notification from the system tray.
     * @param task: The task that is being notified about.
     */
    private void showSystemTrayNotification(ReadOnlyTask task, int type) {
        SystemTray tray = SystemTray.getSystemTray();
        ImageIcon icon = new ImageIcon(getClass().getResource(IMAGE_ICON_PATH));
        java.awt.Image image = icon.getImage();
        TrayIcon trayIcon = new TrayIcon(image, NOTIFICATION_TEXT);
        trayIcon.setImageAutoSize(true);

        String displayMsg = "";
        if (type == DEADLINE_TYPE) {
            trayIcon.setToolTip(task.getName().fullName + DUE_TIME_WITH_NAME_TEXT);
            displayMsg += DUE_TIME_WITHOUT_NAME_TEXT;
            if (!task.getEndDateTime().get().getTimeOnly().isEmpty()) {
                String time = task.getEndDateTime().get().getTimeOnly();
                displayMsg += AT_TEXT + time.substring(0, time.length() - REMOVE_SECONDS_OFFSET) + FULL_STOP_TEXT;
            } else {
                displayMsg += FULL_STOP_TEXT;
            }
        }
        if (type == EVENT_TYPE) {
            trayIcon.setToolTip(task.getName().fullName + START_TIME_WITH_NAME_TEXT);
            displayMsg += START_TIME_WITHOUT_NAME_TEXT;
            if (!task.getStartDateTime().get().getTimeOnly().isEmpty()) {
                String time = task.getStartDateTime().get().getTimeOnly();
                displayMsg += AT_TEXT + time.substring(0, time.length() - REMOVE_SECONDS_OFFSET) + FULL_STOP_TEXT;
            } else {
                displayMsg += FULL_STOP_TEXT;
            }
        }

        MenuItem dismissMenuItem = new MenuItem(DISMISS_TEXT);
        dismissMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
            }
        });

        PopupMenu popupMenu = new PopupMenu();
        popupMenu.add(dismissMenuItem);

        trayIcon.setPopupMenu(popupMenu);
        try {
            tray.add(trayIcon);
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        trayIcon.displayMessage(task.getName().fullName, displayMsg, MessageType.INFO);
    }

    /**
     * Removes all existing scheduled notifications and reschedule them based on the new TaskList
     * @param event: Contains the new TaskList modified due to an event
     */
    public void updateNotifications(TaskManagerChangedEvent event) {
        notificationTimer.cancel(); //remove old scheduled notifications
        notificationTimer = new Timer();
        ObservableList<ReadOnlyTask> taskList = event.data.getTaskList();
        initNotificationTimerWithTasks(taskList);
    }

    /**
     * Initialized the scheduling of tasks
     */
    public void start() {
        initNotificationTimerWithTasks(logic.getFilteredTaskList());
    }

    /**
     * Stops remaining notifications in timer and destroy the timer object
     */
    public void stop() {
        notificationTimer.cancel();
    }

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

    /*private boolean checkIfValid(ReadOnlyTask task) {
        Date tomorrow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tomorrow);
        calendar.add(Calendar.DATE, 1);
        tomorrow = calendar.getTime();
        if (task.getEndDateTime().isPresent()) {
            if (task.getEndDateTime().get().isSameDate(tomorrow)) {
                return true;
            }
        }
        return false;
    }*/

}
