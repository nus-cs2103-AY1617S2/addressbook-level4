//@@author A0139925U
package seedu.tache.ui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

import javafx.application.Platform;
import javafx.stage.Stage;

public class HotkeyManager {
    public static final String DEFAULT_HOTKEY_COMBINATION = "control alt D";
    public static final String LUCKY_DRAW_NOTIFICATION_COMBINATION = "alt Q";

    private Provider hotkeyManager;
    private Stage stage;
    private String hotkeyCombination;
    private Timer notificationTimer;

    public HotkeyManager(Stage stage) {
        this.stage = stage;
        this.hotkeyCombination = DEFAULT_HOTKEY_COMBINATION;
        this.notificationTimer = new Timer();
    }

    public HotkeyManager(Stage stage, String hotkeyCombination) {
        this.stage = stage;
        this.hotkeyCombination = hotkeyCombination;
        this.notificationTimer = new Timer();
    }

    /**
     * Bind show/hide (toggle) hotkey
     */
    private void bindToggleSystemHotkey() {
        if (hotkeyManager != null) {
            hotkeyManager = Provider.getCurrentProvider(false);
        }
        hotkeyManager.register(KeyStroke.getKeyStroke(hotkeyCombination), new HotKeyListener() {
            public void onHotKey(HotKey hotKey) {
                System.out.println(hotKey);
                if (stage.isFocused()) {
                    if (stage.isIconified()) {
                        Platform.runLater(()-> {
                            stage.setIconified(false); });
                    } else {
                        Platform.runLater(()-> {
                            stage.setIconified(true); });
                    }
                } else {
                    Platform.runLater(()-> {
                        stage.setIconified(false);
                        stage.toFront(); });
                }
            }
        });
    }

    /**
     * Bind lucky draw notification trigger hotkey
     */
    private void bindTriggerNotificationHotkey() {
        if (hotkeyManager != null) {
            hotkeyManager = Provider.getCurrentProvider(false);
        }
        hotkeyManager.register(KeyStroke.getKeyStroke(LUCKY_DRAW_NOTIFICATION_COMBINATION), new HotKeyListener() {
            public void onHotKey(HotKey hotKey) {
                System.out.println(hotKey);
                Platform.runLater(()-> {
                    notificationTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                SystemTray tray = SystemTray.getSystemTray();
                                ImageIcon icon = new ImageIcon(getClass().getResource("/images/info_icon.png"));
                                java.awt.Image image = icon.getImage();
                                TrayIcon trayIcon = new TrayIcon(image, "notification");
                                trayIcon.setImageAutoSize(true);
                                trayIcon.setToolTip("Lucky Draw Reminder");

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
                                trayIcon.displayMessage("Lucky Draw Reminder",
                                                        "This task is due now.", MessageType.INFO);
                            } catch (AWTException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 0); //0 indicate that it will only be scheduled once
                });
            }
        });
    }

    /**
     * Starts binding of toggle hotkey
     */
    public void start() {
        bindToggleSystemHotkey();
        bindTriggerNotificationHotkey();
    }

    /**
     * Stops binding of toggle hotkey
     */
    public void stop() {
        if (hotkeyManager != null) {
            hotkeyManager.reset();
            hotkeyManager.stop();
            notificationTimer.cancel();
        }
    }
}
