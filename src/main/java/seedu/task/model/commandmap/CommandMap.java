package seedu.task.model.commandmap;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.AliasCommand;
import seedu.task.logic.commands.CompleteCommand;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.ExportCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.ImportCommand;
import seedu.task.logic.commands.IncompleteCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.RedoCommand;
import seedu.task.logic.commands.ResetCommand;
import seedu.task.logic.commands.RevertCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.logic.commands.UnrevertCommand;
import seedu.task.logic.commands.UseCommand;

public class CommandMap {

    private HashMap<String, String> commandMap;
    private final ArrayList<String> originalCommandWords;

    public CommandMap() {
        this.commandMap = new HashMap<String, String>();
        this.originalCommandWords = getOriginalCommandWords();
    }

    public void putAll(HashMap<String, String> map) {
        this.commandMap.putAll(map);
    }

    private static ArrayList<String> getOriginalCommandWords() {
        ArrayList<String> commandWords = new ArrayList<String>();

        commandWords.add(AliasCommand.COMMAND_WORD);
        commandWords.add(AddCommand.COMMAND_WORD);
        commandWords.add(EditCommand.COMMAND_WORD);
        commandWords.add(CompleteCommand.COMMAND_WORD);
        commandWords.add(IncompleteCommand.COMMAND_WORD);
        commandWords.add(SelectCommand.COMMAND_WORD);
        commandWords.add(DeleteCommand.COMMAND_WORD);
        commandWords.add(ResetCommand.COMMAND_WORD);
        commandWords.add(FindCommand.COMMAND_WORD);
        commandWords.add(ListCommand.COMMAND_WORD);
        commandWords.add(ExitCommand.COMMAND_WORD);
        commandWords.add(HelpCommand.COMMAND_WORD);
        commandWords.add(RedoCommand.COMMAND_WORD);
        commandWords.add(UndoCommand.COMMAND_WORD);
        commandWords.add(ImportCommand.COMMAND_WORD);
        commandWords.add(ExportCommand.COMMAND_WORD);
        commandWords.add(RevertCommand.COMMAND_WORD);
        commandWords.add(UnrevertCommand.COMMAND_WORD);
        commandWords.add(UseCommand.COMMAND_WORD);

        return commandWords;
    }

    /**
     * Signals that the operation refers to a non-existing command.
     */
    public static class OriginalCommandNotFoundException extends Exception {
        public OriginalCommandNotFoundException() {
            super("Original command not found!");
        }
    }

    public static class BaseCommandNotAllowedAsAliasException extends Exception {}

    public boolean containsKey(String commandToMap) {
        return this.commandMap.containsKey(commandToMap);
    }

    public boolean isBaseCommandWord(String command) {
        return this.originalCommandWords.contains(command);
    }

    public String get(String commandToMap) {
        return this.commandMap.get(commandToMap);
    }

    public boolean isOriginalCommandValid(String command) {
        return this.commandMap.containsValue(command)
                || isBaseCommandWord(command);
    }

    public void put(String alias, String commandToMap) {
        this.commandMap.put(alias, commandToMap);
    }

    public HashMap<String, String> getCommandMap() {
        HashMap<String, String> copyMap = new HashMap<String, String>();
        copyMap.putAll(commandMap);
        return copyMap;
    }

    public String translateCommand(String original) {
        String commandToMap = original;
        while (commandMap.containsKey(commandToMap)) {
            commandToMap = commandMap.get(commandToMap);
        }
        return commandToMap;
    }
}
