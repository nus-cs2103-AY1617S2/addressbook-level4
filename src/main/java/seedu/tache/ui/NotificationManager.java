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
import java.net.MalformedURLException;
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

public class NotificationManager {

    private Logic logic;
    private Timer notificationTimer;

    public NotificationManager() {
        notificationTimer = new Timer();
    }

    public NotificationManager(Logic logic) {
        this.logic = logic;
        notificationTimer = new Timer();
    }

    /**
     * Sets a notification timer to tasks that are due tomorrow. The notification timer
     * will then call showSystemTrayNotification method.
     * @param taskList: Lists of tasks from user's data storage file.
     */
    private void initTasksWithNotificationTimer(ObservableList<ReadOnlyTask> taskList) {
        for (ReadOnlyTask task : taskList) {
            if (task.getEndDateTime().isPresent() && isDueInMoreThanTwoHours(task.getEndDateTime().get())) {
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
                }, getTwoHoursBefore(task.getEndDateTime().get())); //0 indicate that it will only be scheduled once
            }
        }
    }

/*    private boolean checkIfValid(ReadOnlyTask task) {
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

    private Date getTwoHoursBefore(DateTime dateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime.getDate());
        cal.add(Calendar.HOUR, -1);
        cal.add(Calendar.MINUTE, -59);
        cal.add(Calendar.SECOND, -57);
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

    public void updateNotifications(TaskManagerChangedEvent event) {
        notificationTimer.cancel(); //remove old scheudled notifications
        notificationTimer = new Timer();
        ObservableList<ReadOnlyTask> taskList = event.data.getTaskList();
        initTasksWithNotificationTimer(taskList);
    }

    /**
     * Starts adding notifications to the timer
     */
    public void start() {
        initTasksWithNotificationTimer(logic.getFilteredTaskList());
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

}
