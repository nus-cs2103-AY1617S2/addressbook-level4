//@@author A0164103W
package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.logic.history.TaskMemento;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Used to implement undo and redo commands.
 * This class contains all the logic required to replace a current ask in model with a memento,
 * the undo or redo command needs to specify which memento to replace the task with.
 */
public abstract class UndoRedoCommand extends Command {
    protected static String MESSAGE_SUCCESS;

    TaskMemento memento;

    /**
     * @param noHistory UI message to show when there are no command to redo or undo
     * @param success UI message to show when the redo or undo command was successful
     */
    UndoRedoCommand(String success) {
        MESSAGE_SUCCESS = success;
    }

    /**
     *Replaces the appropriate task in model with memento task, deletes the task in model,
     *or adds the memento task to the model
     */
    @Override
    public CommandResult execute() throws CommandException {
        memento = getMemento();
        final Task mementoTask = getMementoTask();
        final TaskId mementoTaskId = memento.taskId;
        Task taskToBeReplaced = model.getTaskList().getTaskById(mementoTaskId);

        assert !(mementoTask == null && taskToBeReplaced == null);

        if (mementoTask == null && taskToBeReplaced != null) {
            try {
                model.deleteTask(taskToBeReplaced);
            } catch (TaskNotFoundException e) {
                assert false : "The target task cannot be missing";
            }
        }

        if (taskToBeReplaced == null && mementoTask != null) {
            try {
                model.addTask(mementoTask);
            } catch (DuplicateTaskException e) {
                assert false : "Adding duplicate task";
            }
        }

        if (taskToBeReplaced != null && mementoTask != null) {
            try {
                model.updateTaskById(mementoTaskId, mementoTask);
            } catch (DuplicateTaskException e) {
                assert false : "duplicate task";
            }
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    abstract TaskMemento getMemento() throws CommandException;

    abstract Task getMementoTask() throws CommandException;

}
