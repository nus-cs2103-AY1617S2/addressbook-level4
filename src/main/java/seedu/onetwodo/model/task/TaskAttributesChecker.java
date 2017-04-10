package seedu.onetwodo.model.task;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.exceptions.CommandException;

/**
 * A utility class that checks if the given Task has valid attributes.
 */
public class TaskAttributesChecker {

    public static final String MESSAGE_MISSING_DATE = "A task with a start date needs an end date or duration.";
    public static final String MESSAGE_INVALID_START = "Start time needs to be later than current time.";
    public static final String MESSAGE_INVALID_END = "End time needs to be later than current time.";
    public static final String MESSAGE_INVALID_EVENT = "End time needs to be later than start time.";
    public static final String MESSAGE_INVALID_RECUR = "Task without date cannot be recurring";

    public static void checkValidAttributes(Task taskUnderTest, LocalDateTime dateCreated)
            throws IllegalValueException {
        checkIsValidTodo(taskUnderTest);
        checkIsValidStartDate(taskUnderTest, dateCreated);
        checkIsValidEndDate(taskUnderTest, dateCreated);
        checkIsValidEvent(taskUnderTest);
        checkIsValidRecur(taskUnderTest);
    }

    public static void checkIsValidTodo(Task taskUnderTest) throws IllegalValueException {
        // has start date but no end date
        if (taskUnderTest.getStartDate().hasDate() && !taskUnderTest.getEndDate().hasDate()) {
            throw new IllegalValueException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_DATE) + AddCommand.MESSAGE_USAGE);
        }
    }

    public static void checkIsValidStartDate(Task taskUnderTest, LocalDateTime dateCreated)
            throws IllegalValueException {
        if (taskUnderTest.getStartDate().hasDate()) {
            // startDate of Task is before the date the task is created
            if (taskUnderTest.getStartDate().getLocalDateTime().isBefore(dateCreated)) {
                throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_START)
                        + AddCommand.MESSAGE_USAGE);
            }
        }
    }

    public static void checkIsValidEndDate(Task taskUnderTest, LocalDateTime dateCreated) throws IllegalValueException {
        if (taskUnderTest.getEndDate().hasDate()) {
            // endDate of Task is before the date the task is created
            if (taskUnderTest.getEndDate().getLocalDateTime().compareTo(dateCreated) < 0) {
                throw new IllegalValueException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_END) + AddCommand.MESSAGE_USAGE);
            }
        }
    }

    public static void checkIsValidEvent(Task taskUnderTest) throws IllegalValueException {
        if (taskUnderTest.getStartDate().hasDate() && taskUnderTest.getEndDate().hasDate()) {
            // endDate of Task is before startDate
            if ((taskUnderTest.getEndDate().getLocalDateTime()
                    .isBefore(taskUnderTest.getStartDate().getLocalDateTime()))
                    || (taskUnderTest.getEndDate().getLocalDateTime()
                            .isEqual(taskUnderTest.getStartDate().getLocalDateTime()))) {
                throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_EVENT)
                        + AddCommand.MESSAGE_USAGE);
            }
        }
    }

    public static void validateEditedAttributes(Task editedTask) throws CommandException {
        TaskType type = editedTask.getTaskType();
        if (type == null) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_DATE) + AddCommand.MESSAGE_USAGE);
        }
        StartDate startDate = editedTask.getStartDate();
        EndDate endDate = editedTask.getEndDate();
        switch (type) {
        case DEADLINE:
            // endDate of Task is before the date the task is created
            if (startDate.hasDate() || !endDate.hasDate()) {
                throw new CommandException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_END) + AddCommand.MESSAGE_USAGE);
            }
            break;
        case EVENT:
            if (endDate.getLocalDateTime().isBefore(startDate.getLocalDateTime())) {
                // endDate of Task is before startDate
                throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_EVENT)
                        + AddCommand.MESSAGE_USAGE);
            }
            break;
        case TODO:
            if (startDate.hasDate() || endDate.hasDate()) {
                throw new CommandException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_DATE) + AddCommand.MESSAGE_USAGE);
            }
        }
    }

    // @@author A0139343E
    public static void checkIsValidRecur(Task taskUnderTest) throws IllegalValueException {
        if (!taskUnderTest.getStartDate().hasDate() && !taskUnderTest.getEndDate().hasDate()) {
            if (taskUnderTest.hasRecur()) {
                throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_RECUR)
                        + AddCommand.MESSAGE_USAGE);
            }
        }
    }
}
