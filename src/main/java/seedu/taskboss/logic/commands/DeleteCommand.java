package seedu.taskboss.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from TaskBoss.
 */
public class DeleteCommand extends Command {

    private static final String NUMBERING_DOT = ". ";
    private static final int INDEX_ONE = 1;

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_WORD_SHORT = "d";
    public static final String COMMAND_WORD_2ND_SHORT = "-";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " || " + COMMAND_WORD_SHORT + " 1"
            + " || "  + COMMAND_WORD_2ND_SHORT + "1 2 3";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task(s):\n%1$s";

    public final ArrayList<Integer> targetIndex;

    public final ArrayList<ReadOnlyTask> tasksToDelete;
    //@@author A0138961W
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

        if ((lastShownList.size() < targetIndex.get(0)) || (targetIndex.get(targetIndex.size() - 1) == 0)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        for (int index: targetIndex) {
            ReadOnlyTask taskToDelete = lastShownList.get(index - 1);

            tasksToDelete.add(taskToDelete);
        }

        try {
            model.deleteTask(tasksToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, getDesiredTasksToDeleteFormat()));
    }
    //@@author

    //@@author A0143157J
    /**
     * Returns a formatted {@code ArrayList} tasksToDelete,
     * so that each ReadOnlyTask in the ArrayList is numbered
     */
    private String getDesiredTasksToDeleteFormat() {
        int i = INDEX_ONE;
        StringBuilder builder = new StringBuilder();
        for (ReadOnlyTask task : tasksToDelete) {
            builder.append(i + NUMBERING_DOT).append(task.toString());
            i++;
        }
        return builder.toString();
    }
}
