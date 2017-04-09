package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0163848R
/**
 * Represents a Start Date in the YTomorrow.
 */
public class StartDate extends Date {
    public StartDate(String date) throws IllegalValueException {
        super(date);
    }
}
