package seedu.watodo.logic.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//@@author A0143076J
/**
 * Stores a map of alternatives command words, and their corresponding default
 * command word. Also stores a list of all the default command words of Watodo.
 */
public class AlternativeCommand {

    public static final Set<String> COMMANDS_WORDS = new HashSet<String>() {
        {
            add(AddCommand.COMMAND_WORD);
            add(ClearCommand.COMMAND_WORD);
            add(DeleteCommand.COMMAND_WORD);
            add(EditCommand.COMMAND_WORD);
            add(ExitCommand.COMMAND_WORD);
            add(FindCommand.COMMAND_WORD);
            add(HelpCommand.COMMAND_WORD);
            add(ListAllCommand.ARGUMENT);
            add(ListCommand.COMMAND_WORD);
            add(ListDateCommand.ARGUMENT);
            add(ListDeadlineCommand.ARGUMENT);
            add(ListDoneCommand.ARGUMENT);
            add(ListEventCommand.ARGUMENT);
            add(ListFloatCommand.ARGUMENT);
            add(MarkCommand.COMMAND_WORD);
            add(UnmarkCommand.COMMAND_WORD);
            add(ShortcutCommand.COMMAND_WORD);
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
            // put("r", RedoCommand.COMMAND_WORD);
            // put("u", UndoCommand.COMMAND_WORD);
        }
    };

    /** Returns true if the given commandWord is an alternative command word. */
    public static boolean containsAlternative(String commandWord) {
        return altCommands.containsKey(commandWord);
    }

    /** Returns the standard CommandWord for the given alternative word. */
    public static String getStandardCommandWord(String commandWord) {
        return altCommands.get(commandWord);
    }

    /** Adds a new alternativeCommand format to the hashMap */
    public static void addAlternative(String shortcutKey, String commandWord) {
        altCommands.put(shortcutKey, commandWord);
    }

    /** Deletes the given shortcut key only if its corresponding standard commandWord matches what is given */
    public static boolean deleteAlternative(String shortcutKey, String commandWord) {
        return altCommands.remove(shortcutKey, commandWord);
    }
}
