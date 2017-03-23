package savvytodo.ui.hotkeys;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * Pre-defined hotkeys for specific commands. See UserGuide for definitions.
 * @author jingloon
 *
 */
public abstract class HotKeysCombinations {

    public static final KeyCombination KEYS_EXIT = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
    public static final KeyCombination KEYS_HELP = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN);
    public static final KeyCombination KEYS_LIST = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
    public static final KeyCombination KEYS_CLEAR = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
    public static final KeyCombination KEYS_UNDO = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    public static final KeyCombination KEYS_REDO = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);

}
