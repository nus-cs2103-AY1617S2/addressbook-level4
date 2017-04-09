package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

//@@author A0163848R
/**
* Represents an End Date in the YTomorrow.
*/
public class EndDate extends Date {
    public EndDate(String date) throws IllegalValueException {
        super(date);

        if (date == null) {
            inputValue = null;
            value = new java.util.Date(Long.MAX_VALUE);
        }
    }
}
