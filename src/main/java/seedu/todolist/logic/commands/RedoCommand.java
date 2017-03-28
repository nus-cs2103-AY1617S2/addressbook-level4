package seedu.todolist.logic.commands;

import seedu.todolist.logic.CommandAndState;
import seedu.todolist.model.ReadOnlyToDoList;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_REDO_TASK_SUCCESS = "Redone - %1$s";
    public static final String MESSAGE_NOTHING_TO_REDO = "Nothing to redo!";
    private CommandAndState commAndState;
    private String commandText;

    public RedoCommand() {
    }

    @Override
    public CommandResult execute() {
        if (undoManager.getRedoStack().empty()) {
            return new CommandResult(MESSAGE_NOTHING_TO_REDO);
        }
        commAndState =  undoManager.getCommandAndStateToRedo();
        undoManager.redoLatestUndoneTask();
        String command = commAndState.getCommand().getCommandText();
        ReadOnlyToDoList currentState = commAndState.getStateAfterCommand();
        model.resetData(currentState);
        commandText = String.format(MESSAGE_REDO_TASK_SUCCESS, command);
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
