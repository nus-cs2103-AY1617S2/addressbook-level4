package seedu.todolist.logic.commands;

import seedu.todolist.logic.CommandAndState;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.ReadOnlyToDoList;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_UNDO_PERSON_SUCCESS = "Undone: %1$s";
    private CommandAndState commAndState;
    private String commandText;

    public UndoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        commAndState =  undoManager.getCommandAndStateToUndo();
        undoManager.undoLatestTask();
        String command = commAndState.getCommand().getCommandText();
        ReadOnlyToDoList previousState = commAndState.getState();
        model.resetData(previousState);
        commandText = String.format(MESSAGE_UNDO_PERSON_SUCCESS, command);
        return new CommandResult(commandText);
    }

    @Override
    public boolean isMutating() {
        return false;
    }

    @Override
    public String getCommandText() {
        return commandText;
    }

}
