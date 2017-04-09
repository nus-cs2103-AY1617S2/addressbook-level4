package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0163848R
/**
* Represents an End Date in the YTomorrow.
*/
public class EndDate extends Date {
    public EndDate(String date) throws IllegalValueException {
        super(date);
    }
}
