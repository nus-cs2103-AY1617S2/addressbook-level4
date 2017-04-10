//@@author A0139925U
package seedu.tache.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList;

/**
 * Mark existing task(s) as completed
 */
public class CompleteCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "complete";
    public static final String SHORT_COMMAND_WORD = "c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Completes the task identified "
            + "by the index number used in the last tasks listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX1 (must be a positive integer), INDEX2, INDEX3, ... \n"
            + "Example: " + COMMAND_WORD + " 1, 2, 6, 8";

    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Completed Task(s): \n%1$s";
    public static final String MESSAGE_NOT_COMPLETED = "At least one task's index must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final List<Integer> indexList;

    private boolean commandSuccess;
    private List<ReadOnlyTask> completedList;

    /**
     * @param {@code indexList}, the list of indexes that will be marked as completed
     */
    public CompleteCommand(List<Integer> indexList) {
        assert indexList.size() > 0;
        this.indexList = indexList;

        // converts indexList from one-based to zero-based.
        for (int i = 0; i < indexList.size(); i++) {
            this.indexList.set(i, indexList.get(i) - 1);
        }
        Collections.reverse(indexList);
        completedList = new ArrayList<ReadOnlyTask>();
        commandSuccess = false;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = new ArrayList<ReadOnlyTask>(model.getFilteredTaskList());

        ArrayList<ReadOnlyTask> tasksToEdit = new ArrayList<ReadOnlyTask>();
        ArrayList<ReadOnlyTask> completedTasks = new ArrayList<ReadOnlyTask>();

        checkAllIndexValid(lastShownList);

        commandSuccess = processNonRecurringTask(lastShownList, tasksToEdit, completedTasks)
                        && processRecurringTask(lastShownList, tasksToEdit, completedTasks);

        UndoHistory.getInstance().push(this);

        return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, getSuccessMessage(completedList)));
    }

    /**
     * Checks that all indexes given in the input is valid.
     * @throws CommandException, if any of the indexes is detected as invalid
     * @param {@code lastShownList}, is used to determine the max size;
     */
    private void checkAllIndexValid(List<ReadOnlyTask> lastShownList) throws CommandException {
        for (int i = 0; i < indexList.size(); i++) {
            if (indexList.get(i) >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        }
    }

    /**
     * Processes all non-recurring tasks, marking them as completed
     * @throws CommandException, if duplicates are detected
     * @param {@code lastShownList}, is used get the corresponding task object;
     * @param {@code tasksToEdit}, stores all original uncompleted tasks in this process;
     * @param {@code completedTasks}, stores all modified completed tasks in this process;
     */
    private boolean processNonRecurringTask(List<ReadOnlyTask> lastShownList,
                                            ArrayList<ReadOnlyTask> tasksToEdit,
                                            ArrayList<ReadOnlyTask> completedTasks) throws CommandException {
        for (int i = 0; i < indexList.size(); i++) {
            ReadOnlyTask taskToEdit = lastShownList.get(indexList.get(i));
            if (taskToEdit.getRecurState().isRecurring()) {
                continue;
            } else {
                Task completedTask = createCompletedTask(taskToEdit);
                completedTasks.add(completedTask);
                tasksToEdit.add(taskToEdit);
            }
        }
        ReadOnlyTask[] arrayListMould = new ReadOnlyTask[0];
        try {
            completedList = model.updateMultipleTasks(
                    tasksToEdit.toArray(arrayListMould), completedTasks.toArray(arrayListMould));
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            assert false : "There shouldn't be a duplicate task";
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        return true;
    }

    /**
     * Processes all recurring tasks, marking them as completed
     * @throws CommandException, if duplicates are detected
     * @param {@code lastShownList}, is used get the corresponding task object;
     * @param {@code tasksToEdit}, stores all original uncompleted tasks in this process;
     * @param {@code completedTasks}, stores all modified completed tasks in this process;
     */
    private boolean processRecurringTask(List<ReadOnlyTask> lastShownList,
                                        ArrayList<ReadOnlyTask> tasksToEdit,
                                        ArrayList<ReadOnlyTask> completedTasks) throws CommandException {
        for (int i = 0; i < indexList.size(); i++) {
            ReadOnlyTask taskToEdit = lastShownList.get(indexList.get(i));
            Task completedTask = createCompletedTask(taskToEdit);
            try {
                if (taskToEdit.getRecurState().isRecurring()) {
                    if (taskToEdit.getRecurState().isRecurring()) {
                        model.updateTask(createMasterRecurringTask(taskToEdit), completedTask);
                    } else {
                        model.updateTask(taskToEdit, completedTask);
                    }
                    completedList.add(completedTask);
                }
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
        }
        return true;
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createCompletedTask(ReadOnlyTask taskToEdit) {
        assert taskToEdit != null;
        if (taskToEdit.getRecurState().isGhostRecurring()) {
            List<Date> tempList = taskToEdit.getRecurState().getRecurCompletedList();
            tempList.add(new Date(taskToEdit.getRecurState().getRecurDisplayDate()));
            ((Task) taskToEdit).getRecurState().setRecurDisplayDate("");
            return new Task(taskToEdit.getName(), taskToEdit.getStartDateTime(), taskToEdit.getEndDateTime(),
                            taskToEdit.getTags(), taskToEdit.getActiveStatus(),
                            taskToEdit.getRecurState().getRecurInterval(), tempList);
        } else {
            return new Task(taskToEdit.getName(), taskToEdit.getStartDateTime(), taskToEdit.getEndDateTime(),
                            taskToEdit.getTags(), false,
                            taskToEdit.getRecurState().getRecurInterval(),
                            taskToEdit.getRecurState().getRecurCompletedList());
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createUncompletedTask(ReadOnlyTask taskToEdit) {
        assert taskToEdit != null;

        if (taskToEdit.getRecurState().isMasterRecurring()) {
            List<Date> tempList = taskToEdit.getRecurState().getRecurCompletedList();
            tempList.remove(tempList.size() - 1);
            return new Task(taskToEdit.getName(), taskToEdit.getStartDateTime(), taskToEdit.getEndDateTime(),
                                taskToEdit.getTags(), true,
                                taskToEdit.getRecurState().getRecurInterval(), tempList);
        } else {
            return new Task(taskToEdit.getName(), taskToEdit.getStartDateTime(), taskToEdit.getEndDateTime(),
                                taskToEdit.getTags(), true,
                                taskToEdit.getRecurState().getRecurInterval(),
                                taskToEdit.getRecurState().getRecurCompletedList());
        }

    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createMasterRecurringTask(ReadOnlyTask taskToEdit) {
        assert taskToEdit != null;
        ((Task) taskToEdit).getRecurState().setRecurDisplayDate("");
        return new Task(taskToEdit.getName(), taskToEdit.getStartDateTime(), taskToEdit.getEndDateTime(),
                            taskToEdit.getTags(), taskToEdit.getActiveStatus(),
                            taskToEdit.getRecurState().getRecurInterval(),
                            taskToEdit.getRecurState().getRecurCompletedList());

    }

    /**
     * Creates and returns a formatted String message with the details of {@code completedList}
     */
    private static String getSuccessMessage(List<ReadOnlyTask> completedList) {
        assert completedList != null;
        String successMessage = "";
        for (int i = 0; i < completedList.size(); i++) {
            successMessage += completedList.get(i).getAsText();
        }
        return successMessage;
    }

    @Override
    public boolean isUndoable() {
        return commandSuccess;
    }

    /**
     * Undo any changes made by this command
     */
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
