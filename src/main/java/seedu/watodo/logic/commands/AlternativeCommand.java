package seedu.watodo.logic.commands;

import java.util.HashMap;

/**
 * Stores a map of alternatives command words, and their corresponding standard command word.
 */
public class AlternativeCommand {

    public static HashMap<String, String> altCommands = new HashMap<String, String>();

    public AlternativeCommand() {
        altCommands.put("a", AddCommand.COMMAND_WORD);
        altCommands.put("e", EditCommand.COMMAND_WORD);
        altCommands.put("d", DeleteCommand.COMMAND_WORD);
        altCommands.put("l", ListCommand.COMMAND_WORD);
        altCommands.put("m", MarkCommand.COMMAND_WORD);
        altCommands.put("check", MarkCommand.COMMAND_WORD);
        altCommands.put("um", UnmarkCommand.COMMAND_WORD);
        altCommands.put("uncheck", MarkCommand.COMMAND_WORD);
        altCommands.put("f", FindCommand.COMMAND_WORD);
        altCommands.put("search", FindCommand.COMMAND_WORD);
        //altCommands.put("r", RedoCommand.COMMAND_WORD);
        //altCommands.put("u", UndoCommand.COMMAND_WORD);
    }

    /** Returns true if the given commandWord is an alternative command word. */
    public boolean containsAlternative(String commandWord) {
        return altCommands.containsKey(commandWord);
    }

    /** Returns the standard CommandWord for the given alternative word. */
    public String getStandardCommandWord(String commandWord) {
        return altCommands.get(commandWord);
    }

}
