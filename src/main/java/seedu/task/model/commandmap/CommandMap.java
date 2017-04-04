package seedu.task.model.commandmap;

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

    public CommandMap() {
        this.commandMap = new HashMap<String, String>();
        populateCommandMap();
    }

    public void putAll(HashMap<String, String> map) {
        this.commandMap.putAll(map);
    }

    private void populateCommandMap() {
        commandMap.put(AliasCommand.COMMAND_WORD, AliasCommand.COMMAND_WORD);
        commandMap.put(AddCommand.COMMAND_WORD, AddCommand.COMMAND_WORD);
        commandMap.put(EditCommand.COMMAND_WORD, EditCommand.COMMAND_WORD);
        commandMap.put(CompleteCommand.COMMAND_WORD, CompleteCommand.COMMAND_WORD);
        commandMap.put(IncompleteCommand.COMMAND_WORD, IncompleteCommand.COMMAND_WORD);
        commandMap.put(SelectCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD);
        commandMap.put(DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD);
        commandMap.put(ResetCommand.COMMAND_WORD, ResetCommand.COMMAND_WORD);
        commandMap.put(FindCommand.COMMAND_WORD, FindCommand.COMMAND_WORD);
        commandMap.put(ListCommand.COMMAND_WORD, ListCommand.COMMAND_WORD);
        commandMap.put(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD);
        commandMap.put(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD);
        commandMap.put(RedoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD);
        commandMap.put(UndoCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD);
        commandMap.put(ImportCommand.COMMAND_WORD, ImportCommand.COMMAND_WORD);
        commandMap.put(ExportCommand.COMMAND_WORD, ExportCommand.COMMAND_WORD);
        commandMap.put(RevertCommand.COMMAND_WORD, RevertCommand.COMMAND_WORD);
        commandMap.put(UnrevertCommand.COMMAND_WORD, UnrevertCommand.COMMAND_WORD);
        commandMap.put(UseCommand.COMMAND_WORD, UseCommand.COMMAND_WORD);
    }

    /**
     * Signals that the operation refers to a non-existing command.
     */
    public static class OriginalCommandNotFoundException extends Exception {
        public OriginalCommandNotFoundException() {
            super("Original command not found!");
        }
    }

    public boolean containsKey(String commandToMap) {
        return this.commandMap.containsKey(commandToMap);
    }

    public String get(String commandToMap) {
        return this.commandMap.get(commandToMap);
    }

    public boolean containsValue(String commandToMap) {
        return this.commandMap.containsValue(commandToMap);
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
        String commandToMap = commandMap.get(original);
        return commandToMap;
    }
}
