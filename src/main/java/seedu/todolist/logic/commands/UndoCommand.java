package seedu.todolist.logic.commands;

import seedu.todolist.logic.CommandAndState;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.ReadOnlyToDoList;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_UNDO_PERSON_SUCCESS = "Undone: %1$s";
    private CommandAndState commAndState;

    public UndoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        commAndState =  undoManager.getCommandAndStateToUndo();
        undoManager.undoLatestTask();
        Command command = commAndState.getCommand();
        ReadOnlyToDoList previousState = commAndState.getState();
        model.resetData(previousState);
        return new CommandResult(String.format(MESSAGE_UNDO_PERSON_SUCCESS, command));
    }

    @Override
    public boolean isMutating() {
        return false;
    }

}
