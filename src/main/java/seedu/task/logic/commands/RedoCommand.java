//@@author A0164103W
package seedu.task.logic.commands;


import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.logic.history.TaskMemento;
import seedu.task.model.task.Task;

/**
 *Redoes the last undone command
 */
public class RedoCommand extends UndoRedoCommand {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Command redone";
    public static final String MESSAGE_NO_HISTORY = "No commands to redo";

    public RedoCommand() {
        super(MESSAGE_SUCCESS);
    }

    @Override
    TaskMemento getMemento() throws CommandException {
        return mementos.getRedoMemento().orElseThrow(
            () -> new CommandException(MESSAGE_NO_HISTORY));
    }

    @Override
    Task getMementoTask() throws CommandException {
        return memento.newTask;
    }

}
