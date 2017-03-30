package seedu.address.logic.recurrenceparser;

import java.util.Date;

import seedu.address.model.task.Recurrence;

//@@author A0105287E
/**
 * The API of the RecurrenceParser component.
 */
public interface RecurrenceParser {

    /**
     * Uses recurrence interval input value to the DateTimeParser
     */
    Date getNextDate(Date oldDate, Recurrence recurrence);

    /**
     * Parses interval input value
     */
    int getInterval(String input);
}
