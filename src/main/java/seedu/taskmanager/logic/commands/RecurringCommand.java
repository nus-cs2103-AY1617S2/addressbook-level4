package seedu.taskmanager.logic.commands;

import java.util.List;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.DateTimeUtil;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0141102H
/**
 * Recurs a task
 */
public class RecurringCommand extends Command {

    public static final String COMMAND_WORD = "RECUR";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Recurs the entire task identified "
            + "by the index number used in the last task listing.\n" + "Parameters: INDEX NUMBER TYPEOFRECURRENCE\n"
            + "Example: " + COMMAND_WORD + " 1 4 days will result in task #1 being recurred everyday for 4 days"
            + "Example: " + COMMAND_WORD + " 3 3 weeks will result in task #3 being recurred on that date for 3 weeks";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_RECURRING_TASK_SUCCESS = "Task has successfully recurred ";
    public static final String MESSAGE_RECURRING_FLOATING_TASK_FAILURE = "Unable to create recurring "
            + "task for floating task!";
    public static final String RECURRING_TASK_VALIDATION_REGEX = "\\d+\\s+\\d+\\s+[a-zA-Z]+";
    public static final String RECURRING_TASK_VALIDATION_REGEX2 = "[^\\s].*";
    public static final String EMPTY_FIELD = "EMPTY_FIELD";

    private final int taskListIndex;
    private final int numberOfRecurrence;
    private final String typeOfRecurrence;
    private Task recurringTask;

    public RecurringCommand(int taskListIndex, int numberOfRecurrence, String typeOfRecurrence) {
        assert taskListIndex > 0;
        this.taskListIndex = taskListIndex - 1;
        this.numberOfRecurrence = numberOfRecurrence;
        this.typeOfRecurrence = typeOfRecurrence;
    }

    public CommandResult execute() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (taskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToRecur = lastShownList.get(taskListIndex);

        if (taskToRecur.isFloatingTask()) {
            throw new CommandException(MESSAGE_RECURRING_FLOATING_TASK_FAILURE);
        }

        if (taskToRecur.isDeadlineTask()) {

            // try {
            // model.addRecurringDeadlineTask(taskToRecur, numberOfRecurrence,
            // typeOfRecurrence);
            // } catch (DuplicateTaskException dte) {
            // throw new CommandException(MESSAGE_DUPLICATE_TASK);
            // }
            //
            for (int loop = 1; loop <= numberOfRecurrence; loop++) {

                try {
                    recurringTask = new Task(taskToRecur.getTaskName(), taskToRecur.getStartDate(),
                            taskToRecur.getStartTime(),
                            new EndDate(DateTimeUtil.getFutureDate(loop, typeOfRecurrence,
                                    taskToRecur.getEndDate().toString())),
                            taskToRecur.getEndTime(), false, taskToRecur.getCategories());
                } catch (IllegalValueException ive) {
                    throw new CommandException("Wrong format for deadline!");
                }

                if (DateTimeUtil.isValidDate(recurringTask.getEndDate().toString())) {
                    try {
                        model.addTask(recurringTask);
                    } catch (DuplicateTaskException dte) {
                        throw new CommandException(MESSAGE_DUPLICATE_TASK);
                    }
                }
            }
        }

        if (taskToRecur.isEventTask()) {
            for (int loop = 1; loop <= numberOfRecurrence; loop++) {
                try {
                    recurringTask = new Task(taskToRecur.getTaskName(),
                            new StartDate(DateTimeUtil.getFutureDate(loop, typeOfRecurrence,
                                    taskToRecur.getStartDate().toString())),
                            taskToRecur.getStartTime(),
                            new EndDate(DateTimeUtil.getFutureDate(loop, typeOfRecurrence,
                                    taskToRecur.getEndDate().toString())),
                            taskToRecur.getEndTime(), false, taskToRecur.getCategories());
                } catch (IllegalValueException ive) {
                    throw new CommandException("Wrong format for event!");
                }

                if (DateTimeUtil.isValidDate(recurringTask.getStartDate().toString())
                        && DateTimeUtil.isValidDate(recurringTask.getEndDate().toString())) {
                    try {
                        model.addTask(recurringTask);
                    } catch (DuplicateTaskException dte) {
                        throw new CommandException(MESSAGE_DUPLICATE_TASK);
                    }
                }

            }
        }

        model.updateFilteredListToShowAll();
        return new CommandResult(
                String.format(MESSAGE_RECURRING_TASK_SUCCESS, String.valueOf(numberOfRecurrence), " number of times"));
    }
}
