package seedu.ezdo.model.todo;

import seedu.ezdo.commons.exceptions.IllegalValueException;

//@@author A0139177W
/**
 * Represents the due date of a task.
 */
public class DueDate extends TaskDate {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS =
            "Due dates should at least be in the format DD/MM/YYYY HH:MM (24 hours)";


    /**
     * Validates given due date.
     * @param dueDate   The due date of a task.
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueDate(String dueDate) throws IllegalValueException {
        super(dueDate);
        if (!isValidTaskDate(this.value)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
    }

//@@author A0141010L
    /**
     * Validates given due date when user finds by date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueDate(String dueDate, boolean isFind) throws IllegalValueException {
        super(dueDate, isFind);
        if (!isValidTaskDate(this.value)) {
            throw new IllegalValueException(MESSAGE_FIND_DATE_CONSTRAINTS);
        }
    }
}
