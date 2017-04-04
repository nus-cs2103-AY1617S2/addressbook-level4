//@@author A0139925U
package seedu.tache.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.Task.RecurInterval;
import seedu.tache.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task manager.
 */
public class CompleteCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "complete";
    public static final String SHORT_COMMAND_WORD = "c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Completes the task identified "
            + "by the index number used in the last tasks listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX1 (must be a positive integer), INDEX2, INDEX3, ... \n"
            + "Example: " + COMMAND_WORD + " 1, 2, 6, 8";

    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task: \n%1$s";
    public static final String MESSAGE_NOT_COMPLETED = "At least one task's index must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final List<Integer> indexList;

    private boolean commandSuccess;
    private List<ReadOnlyTask> completedList;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param completeTaskDescriptor details to edit the task with
     */
    public CompleteCommand(List<Integer> indexList) {
        assert indexList.size() > 0;
        this.indexList = indexList;

        // converts indexList from one-based to zero-based.
        for (int i = 0; i < indexList.size(); i++) {
            this.indexList.set(i, indexList.get(i) - 1);
        }
        Collections.reverse(indexList);
        commandSuccess = false;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = new ArrayList<ReadOnlyTask>(model.getFilteredTaskList());
        completedList = new ArrayList<ReadOnlyTask>();

        //Check all indexes are valid before proceeding
        for (int i = 0; i < indexList.size(); i++) {
            if (indexList.get(i) >= lastShownList.size()) {
                commandSuccess = false;
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        }

        for (int i = 0; i < indexList.size(); i++) {
            ReadOnlyTask taskToEdit = lastShownList.get(indexList.get(i));
            Task completedTask = createCompletedTask(taskToEdit);
            try {
                model.updateTask(taskToEdit, completedTask);
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                commandSuccess = false;
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            completedList.add(completedTask);
        }
        commandSuccess = true;
        undoHistory.push(this);
        model.updateCurrentFilteredList();

        return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, getSuccessMessage(completedList)));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createCompletedTask(ReadOnlyTask taskToEdit) {
        assert taskToEdit != null;

        return new Task(taskToEdit.getName(), taskToEdit.getStartDateTime(), taskToEdit.getEndDateTime(),
                            taskToEdit.getTags(), taskToEdit.getTimedStatus(), false, false, RecurInterval.NONE);

    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createUncompletedTask(ReadOnlyTask taskToEdit) {
        assert taskToEdit != null;

        return new Task(taskToEdit.getName(), taskToEdit.getStartDateTime(), taskToEdit.getEndDateTime(),
                            taskToEdit.getTags(), taskToEdit.getTimedStatus(), true, false, RecurInterval.NONE);

    }

    /**
     * Creates and returns a formatted String message with the details of {@code completedList}
     */
    private static String getSuccessMessage(List<ReadOnlyTask> completedList) {
        assert completedList != null;
        String successMessage = "";
        for (int i = 0; i < completedList.size(); i++) {
            successMessage += completedList.get(i).toString();
        }
        return successMessage;
    }

    @Override
    public boolean isUndoable() {
        return commandSuccess;
    }

    @Override
    public String undo() throws CommandException {
        for (int i = 0; i < completedList.size(); i++) {
            try {
                ReadOnlyTask original = completedList.get(i);
                ReadOnlyTask revert = createUncompletedTask(completedList.get(i));
                model.updateTask(original, revert);
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
        }
        return String.format(MESSAGE_COMPLETED_TASK_SUCCESS, completedList);
    }
}
