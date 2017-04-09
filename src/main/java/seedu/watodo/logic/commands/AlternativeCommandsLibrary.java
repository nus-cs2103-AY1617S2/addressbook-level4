package seedu.watodo.logic.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.watodo.commons.exceptions.IllegalValueException;

//@@author A0143076J
/**
 * Stores a map of alternatives command words, and their corresponding default
 * command word. Also stores a list of all the default command words of Watodo.
 */
public class AlternativeCommandsLibrary {

    public static final List<String> COMMANDS_WORDS = new ArrayList<String>() {
        {
            add(AddCommand.COMMAND_WORD);
            add(EditCommand.COMMAND_WORD);
            add(DeleteCommand.COMMAND_WORD);
            add(MarkCommand.COMMAND_WORD);
            add(UnmarkCommand.COMMAND_WORD);
            add(ListCommand.COMMAND_WORD);
            add(ListCommand.COMMAND_WORD + " " + ListAllCommand.ARGUMENT);
            add(ListCommand.COMMAND_WORD + " " + ListDeadlineCommand.ARGUMENT);
            add(ListCommand.COMMAND_WORD + " " + ListEventCommand.ARGUMENT);
            add(ListCommand.COMMAND_WORD + " " + ListFloatCommand.ARGUMENT);
            add(ListCommand.COMMAND_WORD + " " + ListDoneCommand.ARGUMENT);
            add(ListCommand.COMMAND_WORD + " " + ListUndoneCommand.ARGUMENT);
            add(FindCommand.COMMAND_WORD);
            add(UndoCommand.COMMAND_WORD);
            add(RedoCommand.COMMAND_WORD);
            add(SelectCommand.COMMAND_WORD);
            add(ShortcutCommand.COMMAND_WORD);
            add(SaveAsCommand.COMMAND_WORD);
            add(ViewFileCommand.COMMAND_WORD);
            add(ViewShortcutsCommand.COMMAND_WORD);
            add(ClearCommand.COMMAND_WORD);
            add(HelpCommand.COMMAND_WORD);
            add(ExitCommand.COMMAND_WORD);
        }
    };

    public static HashMap<String, String> altCommands = new HashMap<String, String>() {
        {
            put("a", AddCommand.COMMAND_WORD);
            put("e", EditCommand.COMMAND_WORD);
            put("d", DeleteCommand.COMMAND_WORD);
            put("del", DeleteCommand.COMMAND_WORD);
            put("l", ListCommand.COMMAND_WORD);
            put("m", MarkCommand.COMMAND_WORD);
            put("check", MarkCommand.COMMAND_WORD);
            put("um", UnmarkCommand.COMMAND_WORD);
            put("uncheck", MarkCommand.COMMAND_WORD);
            put("f", FindCommand.COMMAND_WORD);
            put("search", FindCommand.COMMAND_WORD);
            put("r", RedoCommand.COMMAND_WORD);
            put("u", UndoCommand.COMMAND_WORD);
            put("s", SelectCommand.COMMAND_WORD);

            for (String commandWord : AlternativeCommandsLibrary.COMMANDS_WORDS) {
                put(commandWord, commandWord);
            }
        }
    };

    /** Returns true if the given shortcutKey is an alternative command word. */
    public static boolean isAlternative(String shortcutKey) {
        assert shortcutKey != null;
        return altCommands.containsKey(shortcutKey) && altCommands.get(shortcutKey) != null;
    }

    /** Returns the standard CommandWord for the given shortcutKey. */
    public static String getStandardCommandWord(String shortcutKey) {
        assert shortcutKey != null && isAlternative(shortcutKey);
        return altCommands.get(shortcutKey);
    }

    /** Adds a new alternativeCommand format to the hashMap */
    public static void addAlternative(String shortcutKey, String commandWord) {
        assert shortcutKey != null && !isAlternative(shortcutKey);
        altCommands.put(shortcutKey, commandWord);
    }

    /** Deletes the given shortcut key only if its corresponding standard commandWord matches what is given  */
    public static void deleteAlternative(String shortcutKey, String commandWord) throws IllegalValueException {
        assert shortcutKey != null && isAlternative(shortcutKey);
        boolean isDelSuccess = altCommands.remove(shortcutKey, commandWord);
        if (!isDelSuccess) {
            throw new IllegalValueException(ShortcutCommand.MESSAGE_DELETE_INVALID_SHORTCUT_KEY);
        }
    }
}
