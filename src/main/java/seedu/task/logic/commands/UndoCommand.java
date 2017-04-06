//@@author A0164103W
package seedu.task.logic.commands;


import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.logic.history.TaskMemento;
import seedu.task.model.task.Task;

/**
 * Undo previous commands executed
 */
public class UndoCommand extends UndoRedoCommand {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Command undone";
    public static final String MESSAGE_NO_HISTORY = "No commands to undo";

    public UndoCommand() {
        super(MESSAGE_SUCCESS);
    }

    @Override
    TaskMemento getMemento() throws CommandException {
        return mementos.getUndoMemento().orElseThrow(
            () -> new CommandException(MESSAGE_NO_HISTORY));
    }

    @Override
    Task getMementoTask() throws CommandException {
        return memento.oldTask;
    }

}

