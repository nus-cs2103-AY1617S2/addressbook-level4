package seedu.task.commons.util;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

//@@author A0141928B
public class NotificationUtil {
    private static final String TITLE = "doTASK reminder";

    /**
     * Creates a notification on the system tray
     * @param description the message to be displayed in the notification
     */
    public void displayNotification(String description) {

        SystemTray tray = SystemTray.getSystemTray();

        TrayIcon notification = new TrayIcon(Toolkit.getDefaultToolkit().getImage("icon.png"));

        notification.setImageAutoSize(true);

        try {
            tray.add(notification);
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        notification.displayMessage(TITLE, description, MessageType.NONE);
    }
}
