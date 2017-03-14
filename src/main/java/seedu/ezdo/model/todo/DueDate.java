package seedu.ezdo.model.todo;


import seedu.ezdo.commons.exceptions.IllegalValueException;


/**
 * Represents the due date of a task.
 */
public class DueDate extends TaskDate {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS =
            "Due dates should be in the format DD/MM/YYYY, and it should not be blank";


    /**
     * Validates given due date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueDate(String dueDate) throws IllegalValueException {
        super(dueDate);
        if (!isValidTaskDate(this.value)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
    }

}
