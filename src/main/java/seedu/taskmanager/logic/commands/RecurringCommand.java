package seedu.taskmanager.logic.commands;

import java.time.YearMonth;
import java.util.List;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.CurrentDate;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

public class RecurringCommand extends Command {

    public static final String COMMAND_WORD = "RECUR";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Recurs the entire task identified "
            + "by the index number used in the last task listing.\n" + "Parameters: INDEX NUMBER TYPEOFRECURRENCE\n"
            + "Example: " + COMMAND_WORD + " 1 4 days will result in task #1 being recurred everyday for 4 days"
            + "Example: " + COMMAND_WORD + " 3 3 weeks will result in task #3 being recurred on that date for 3 weeks";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_RECURRING_TASK_SUCCESS = "Task has successfully recurred ";
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

        if (isFloatingTask(taskToRecur)) {
            throw new CommandException("Unable to create recurring task for floating task!");
        }

        if (isDeadline(taskToRecur)) {
            for (int loop = 1; loop <= numberOfRecurrence; loop++) {

                try {
                    recurringTask = new Task(taskToRecur.getTaskName(), taskToRecur.getStartDate(),
                            taskToRecur.getStartTime(),
                            new EndDate(getNewDate(loop, typeOfRecurrence, taskToRecur.getEndDate().toString())),
                            taskToRecur.getEndTime(), false, taskToRecur.getCategories());
                } catch (IllegalValueException ive) {
                    throw new CommandException("Wrong format for deadline!");
                }

                if (CurrentDate.isValidDate(recurringTask.getEndDate().toString())) {
                    try {
                        model.addTask(recurringTask);
                    } catch (DuplicateTaskException dte) {
                        throw new CommandException(MESSAGE_DUPLICATE_TASK);
                    }
                }
            }
        }

        if (isEvent(taskToRecur)) {
            for (int loop = 1; loop <= numberOfRecurrence; loop++) {
                try {
                    recurringTask = new Task(taskToRecur.getTaskName(),
                            new StartDate(getNewDate(loop, typeOfRecurrence, taskToRecur.getStartDate().toString())),
                            taskToRecur.getStartTime(),
                            new EndDate(getNewDate(loop, typeOfRecurrence, taskToRecur.getEndDate().toString())),
                            taskToRecur.getEndTime(), false, taskToRecur.getCategories());
                } catch (IllegalValueException ive) {
                    throw new CommandException("Wrong format for event!");
                }

                if (CurrentDate.isValidDate(recurringTask.getStartDate().toString())
                        && CurrentDate.isValidDate(recurringTask.getEndDate().toString()))
                    try {
                        model.addTask(recurringTask);
                    } catch (DuplicateTaskException dte) {
                        throw new CommandException(MESSAGE_DUPLICATE_TASK);
                    }
            }
        }

        model.updateFilteredListToShowAll();
        return new CommandResult(
                String.format(MESSAGE_RECURRING_TASK_SUCCESS, String.valueOf(numberOfRecurrence), " number of times"));
    }

    private Boolean isFloatingTask(ReadOnlyTask input) {
        if (input.getStartDate().toString().equals(EMPTY_FIELD) && input.getStartTime().toString().equals(EMPTY_FIELD)
                && input.getEndDate().toString().equals(EMPTY_FIELD)
                && input.getEndTime().toString().equals(EMPTY_FIELD)) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isDeadline(ReadOnlyTask input) {
        if (input.getStartDate().toString().equals(EMPTY_FIELD) && input.getStartTime().toString().equals(EMPTY_FIELD)
                && !input.getEndDate().toString().equals(EMPTY_FIELD)
                && !input.getEndTime().toString().equals(EMPTY_FIELD)) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isEvent(ReadOnlyTask input) {
        if (!input.getStartDate().toString().equals(EMPTY_FIELD) && !input.getStartTime().toString().equals(EMPTY_FIELD)
                && !input.getEndDate().toString().equals(EMPTY_FIELD)
                && !input.getEndTime().toString().equals(EMPTY_FIELD)) {
            return true;
        } else {
            return false;
        }
    }

    private String getNewDate(int loops, String typeOfRecurrence, String existingDate) {
        String[] dmy = existingDate.trim().split("/");
        int day = Integer.parseInt(dmy[0]);
        int month = Integer.parseInt(dmy[1]);
        int year = Integer.parseInt(dmy[2]);

        String newDate = "";
        String newDay = "";
        String newMonth = "";

        YearMonth yearMonthObject = YearMonth.of(2000 + year, month);

        if (typeOfRecurrence.equalsIgnoreCase("days")) {
            day = day + loops;
            while (day > yearMonthObject.lengthOfMonth()) {
                day = day - yearMonthObject.lengthOfMonth();
                month = month + 01;
                if (month > 12) {
                    year = year + 1;
                    month = 01;
                }
                yearMonthObject = YearMonth.of(2000 + year, month);
            }
        }

        if (typeOfRecurrence.equalsIgnoreCase("weeks")) {
            day = day + loops * 7;
            while (day > yearMonthObject.lengthOfMonth()) {
                day = day - yearMonthObject.lengthOfMonth();
                month = month + 01;
                if (month > 12) {
                    year = year + 1;
                    month = 01;
                }
                yearMonthObject = YearMonth.of(2000 + year, month);
            }
        }

        if (typeOfRecurrence.equalsIgnoreCase("months")) {
            month = month + loops;
            while (month > 12) {
                month = month - 12;
                year = year + 1;
            }
        }

        if (typeOfRecurrence.equalsIgnoreCase("years")) {
            year = year + loops;
        }

        if (day < 10) {
            newDay = "0" + Integer.toString(day);
        } else {
            newDay = Integer.toString(day);
        }

        if (month < 10) {
            newMonth = "0" + Integer.toString(month);
        } else {
            newMonth = Integer.toString(month);
        }

        newDate = newDay + "/" + newMonth + "/" + year;

        return newDate;
    }
}
