package seedu.ezdo.model.todo;


import seedu.ezdo.commons.exceptions.IllegalValueException;


/**
 * Represents the start date of a task.
 */
public class StartDate extends TaskDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS =
            "Start dates should be in the format DD/MM/YYYY, and it should not be blank";


    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given start date string is invalid.
     */
    public StartDate(String startDate) throws IllegalValueException {
        super(startDate);
        if (!isValidTaskDate(this.value)) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        }
    }

}
