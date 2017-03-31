package savvytodo.ui.hotkeys;

import javafx.scene.input.KeyEvent;
import savvytodo.logic.commands.ClearCommand;
import savvytodo.logic.commands.ExitCommand;
import savvytodo.logic.commands.HelpCommand;
import savvytodo.logic.commands.ListCommand;
import savvytodo.logic.commands.RedoCommand;
import savvytodo.logic.commands.UndoCommand;

//@@author A0147827U
/**
 * Manager class to handle hotkey detection and conversion into proper command
 * @author jingloon
 *
 */
public class HotKeysManager {
    public static final String NOT_HOTKEY = "";

    /**
     * Compares the key event to pre-defined key combinations and returns the correct command.
     * @author jingloon
     * @param event
     * @return the command word as a String
     */
    public static String getCommand(KeyEvent event) {
        String commandWord;

        if (HotKeysCombinations.KEYS_EXIT.match(event)) {
            commandWord = ExitCommand.COMMAND_WORD;

        } else if (HotKeysCombinations.KEYS_HELP.match(event)) {
            commandWord = HelpCommand.COMMAND_WORD;

        } else if (HotKeysCombinations.KEYS_LIST.match(event)) {
            commandWord = ListCommand.COMMAND_WORD;

        } else if (HotKeysCombinations.KEYS_CLEAR.match(event)) {
            commandWord = ClearCommand.COMMAND_WORD;

        } else if (HotKeysCombinations.KEYS_UNDO.match(event)) {
            commandWord = UndoCommand.COMMAND_WORD;

        } else if (HotKeysCombinations.KEYS_REDO.match(event)) {
            commandWord = RedoCommand.COMMAND_WORD;
        } else {
            commandWord = NOT_HOTKEY;
        }

        return commandWord;
    }
}
