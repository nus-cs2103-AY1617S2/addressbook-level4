package seedu.taskboss.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from TaskBoss.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_WORD_SHORT = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " || " + COMMAND_WORD_SHORT + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final ArrayList<Integer> targetIndex;

    public final ArrayList<ReadOnlyTask> tasksToDelete;

    /**
    * Set will automatically remove duplicate indexes
    */
    public DeleteCommand(Set<Integer> targetIndex) {
        this.targetIndex = new ArrayList<Integer>(targetIndex);
        Collections.sort(this.targetIndex);
        Collections.reverse(this.targetIndex);
        this.tasksToDelete = new ArrayList<ReadOnlyTask>();
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        for (int index: targetIndex) {
            ReadOnlyTask taskToDelete = lastShownList.get(index - 1);

            if ((lastShownList.size() < index) || (index == 0)) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            tasksToDelete.add(taskToDelete);
        }

        for (ReadOnlyTask taskToDelete: tasksToDelete) {
            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, tasksToDelete));
    }

}
