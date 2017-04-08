//@@author A0139925U
package seedu.tache.ui;

import javax.swing.KeyStroke;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

import javafx.application.Platform;
import javafx.stage.Stage;

public class HotkeyManager {
    public static final String DEFAULT_HOTKEY_COMBINATION = "control alt D";

    private Provider hotkeyManager;
    private Stage stage;
    private String hotkeyCombination;

    public HotkeyManager(Stage stage) {
        this.stage = stage;
        this.hotkeyCombination = DEFAULT_HOTKEY_COMBINATION;
    }

    /**
     * Bind show/hide (toggle) hotkey
     */
    private void bindToggleSystemHotkey() {
        if (hotkeyManager == null) {
            hotkeyManager = Provider.getCurrentProvider(false);
        }
        hotkeyManager.register(KeyStroke.getKeyStroke(hotkeyCombination), new HotKeyListener() {
            public void onHotKey(HotKey hotKey) {
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
     * Starts binding of toggle hotkey
     */
    public void start() {
        bindToggleSystemHotkey();
    }

    /**
     * Stops binding of toggle hotkey
     */
    public void stop() {
        if (hotkeyManager != null) {
            hotkeyManager.reset();
            hotkeyManager.stop();
        }
    }
}
