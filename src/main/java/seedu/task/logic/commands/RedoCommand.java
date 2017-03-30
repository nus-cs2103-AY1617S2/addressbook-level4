//@@author A0164103W
package seedu.task.logic.commands;

import java.util.Optional;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.logic.history.TaskMemento;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Command redone";
    public static final String MESSAGE_NO_HISTORY = "No commands to redo";

    @Override
    public CommandResult execute() throws CommandException {
        final Optional<TaskMemento> memento = mementos.getRedoMemento();
        final Task mementoTask = memento.orElseThrow(
            () -> new CommandException(MESSAGE_NO_HISTORY)).newTask;
        final TaskId mementoTaskId = memento.orElseThrow(
            () -> new CommandException(MESSAGE_NO_HISTORY)).taskId;
        Task taskToBeReplaced = model.getTaskList().getTaskById(mementoTaskId);

        assert !(mementoTask == null && taskToBeReplaced == null);

        if (mementoTask == null && taskToBeReplaced != null) { //In case of redoing delete
            try {
                model.deleteTask(taskToBeReplaced);
            } catch (TaskNotFoundException e) {
                assert false : "The target task cannot be missing";
            }
        }

        if (taskToBeReplaced == null && mementoTask != null) { //In case of undoing add
            try {
                model.addTask(mementoTask);
            } catch (DuplicateTaskException e) {
                assert false : "Adding duplicate task";
            }
        }

        if (taskToBeReplaced != null && mementoTask != null) { //In case of editing
            try {
                model.updateTaskById(mementoTaskId, mementoTask);
            } catch (DuplicateTaskException e) {
                assert false : "duplicate task";
            }
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
