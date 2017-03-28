//@@author A0144885R
package seedu.address.model.task.date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * A factory class for producing TaskDate objects.
 */
public class DateFactory {

    public DateFactory() {}

    public TaskDate getTaskDateFromString(String dateString) throws IllegalValueException {

        try {
            return ((TaskDate) new TimePeriod(dateString));
        } catch (IllegalValueException e) {
            // Do nothing & continue with next case
        }

        return ((TaskDate) new TimePoint(dateString));
    }

    public TaskDate getUnassignedTime() {
        return (TaskDate) new TimeUnassigned();
    }
}

