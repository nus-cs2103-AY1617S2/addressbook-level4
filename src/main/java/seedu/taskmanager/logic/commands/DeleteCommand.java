package seedu.taskmanager.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.UniqueTaskList.TaskNotFoundException;

// @@author A0142418L
/**
 * Deletes a task identified using it's last displayed index from the task
 * manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "DELETE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task(s) identified by the index number or task name or task date used "
            + "in the last task listing.\n" + "Parameters: INDEX (must be a positive integer)\n" + "Example: "
            + COMMAND_WORD + " 1\n" + "Parameters: TASK NAME (must be a valid task name)\n" + "Example: " + COMMAND_WORD
            + " Eat lunch with mum\n" + "Parameters: TASK DATE (must be a valid task date)\n" + "Example: "
            + COMMAND_WORD + " 15/07/17";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";
    public static final String MESSAGE_DELETE_TASKS_DATE_SUCCESS_1 = "Deleted tasks on: ";
    public static final String MESSAGE_DELETE_TASKS_DATE_SUCCESS_2 = "\nTotal number of task(s) deleted: ";

    public static final String MESSAGE_DELETE_TASKS_NAME_SUCCESS_1 = "Deleted task all tasks with name '";
    public static final String MESSAGE_DELETE_TASKS_NAME_SUCCESS_2 = "' .\nTotal number of task(s) deleted: ";

    public static final String DELETE_COMMAND_VALIDATION_REGEX_1 = "\\d+";
    public static final String DELETE_COMMAND_VALIDATION_REGEX_2 = "[a-zA-Z]+";
    public static final String DELETE_COMMAND_VALIDATION_REGEX_3 = "\\d{2}/\\d{2}/\\d{2}";

    public static final int DELETE_INDEX_NOT_PRESENT_TOKEN = -1;
    public static final String DELETE_TASKNAME_KEYWORD_NOT_PRESENT_TOKEN = "";
    public static final String DELETE_DATE_KEYWORD_NOT_PRESENT_TOKEN = "";

    public static final String MESSAGE_INVALID_TASK_DATE = "There is no task with the date specified to delete.";
    public static final String MESSAGE_INVALID_TASK_NAME = "There is no task with the name specified to delete.";

    public final int targetIndex;
    public final String targetTaskName;
    public final String targetDate;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetTaskName = DELETE_TASKNAME_KEYWORD_NOT_PRESENT_TOKEN;
        this.targetDate = DELETE_DATE_KEYWORD_NOT_PRESENT_TOKEN;
    }

    public DeleteCommand(String targetString) {
        this.targetIndex = DELETE_INDEX_NOT_PRESENT_TOKEN;
        if (targetString.matches(DELETE_COMMAND_VALIDATION_REGEX_3)) {
            this.targetTaskName = DELETE_TASKNAME_KEYWORD_NOT_PRESENT_TOKEN;
            this.targetDate = targetString;
        } else {
            this.targetTaskName = targetString;
            this.targetDate = DELETE_DATE_KEYWORD_NOT_PRESENT_TOKEN;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (targetIndex != DELETE_INDEX_NOT_PRESENT_TOKEN) {

            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

            ReadOnlyTask taskToDelete = findTaskToDeleteByIndex(lastShownList);
            deleteTask(taskToDelete);

            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));

        } else {
            if (!targetDate.equals(DELETE_DATE_KEYWORD_NOT_PRESENT_TOKEN)) {

                UnmodifiableObservableList<ReadOnlyTask> lastShownList = findTasksToDeleteByDate();
                int numDeletedTasks = deleteTasksByDate(lastShownList);

                return new CommandResult(MESSAGE_DELETE_TASKS_DATE_SUCCESS_1 + targetDate
                        + MESSAGE_DELETE_TASKS_DATE_SUCCESS_2 + Integer.toString(numDeletedTasks));
            } else {
                UnmodifiableObservableList<ReadOnlyTask> lastShownList = findTasksToDeleteByName();
                int numDeletedTasks = deleteTasksByName(lastShownList);

                return new CommandResult(MESSAGE_DELETE_TASKS_NAME_SUCCESS_1 + targetTaskName
                        + MESSAGE_DELETE_TASKS_NAME_SUCCESS_2 + Integer.toString(numDeletedTasks));
            }
        }
    }

    /**
     * @param lastShownList
     * @return
     * @throws CommandException
     */
    private ReadOnlyTask findTaskToDeleteByIndex(UnmodifiableObservableList<ReadOnlyTask> lastShownList)
            throws CommandException {
        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
        return taskToDelete;
    }

    /**
     * @return List of tasks to search for exact task name to be deleted
     * @throws CommandException
     */
    private UnmodifiableObservableList<ReadOnlyTask> findTasksToDeleteByName() throws CommandException {
        final String[] keywords = targetTaskName.split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));

        model.updateFilteredTaskList(keywordSet);

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() == 0) {
            model.updateFilteredListToShowAll();
            throw new CommandException(MESSAGE_INVALID_TASK_NAME);
        }
        return lastShownList;
    }

    /**
     * @return List of tasks with same dates to be deleted
     * @throws CommandException
     */
    private UnmodifiableObservableList<ReadOnlyTask> findTasksToDeleteByDate() throws CommandException {
        final String[] keywords = targetDate.split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));

        model.updateFilteredTaskList(keywordSet);

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() == 0) {
            model.updateFilteredListToShowAll();
            throw new CommandException(MESSAGE_INVALID_TASK_DATE);
        }
        return lastShownList;
    }

    /**
     * @param numDeletedTasks
     * @param lastShownList
     * @return Number of tasks deleted based on task name provided by user
     */
    private int deleteTasksByName(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        int numDeletedTasks = 0;
        try {
            numDeletedTasks = model.deleteTasksName(lastShownList, targetTaskName.trim());
        } catch (TaskNotFoundException e) {
            assert false : MESSAGE_INVALID_TASK_NAME;
        }
        return numDeletedTasks;
    }

    /**
     * @param numDeletedTasks
     * @param lastShownList
     * @return Number of tasks deleted based on date provided by user
     */
    private int deleteTasksByDate(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        int numDeletedTasks = 0;

        try {
            numDeletedTasks = model.deleteTasksDate(lastShownList);
        } catch (TaskNotFoundException e) {
            assert false : MESSAGE_INVALID_TASK_DATE;
        }
        return numDeletedTasks;
    }

    /**
     * @param taskToDelete
     */
    private void deleteTask(ReadOnlyTask taskToDelete) {
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
    }
}
