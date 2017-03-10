package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.parser.DeadlineParser;

/**
 * Represents a Task's deadline in the TaskManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public enum DeadlineType {
        DATEONLY, DATETIME
    };

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
        "Task deadline should strictly follow this format DD/MM/YYYY";

    /**
     * Output format used to display deadline with both date and time.
     * Day, Month Date Year at Hour:Minute
     * Example: Tuesday, April 1 2013 at 23:59
     */
    public static final String READABLE_DATETIME_OUTPUT_FORMAT = "EEE, MMM dd yyyy, hh:mm aaa";

    /**
     * Output format used to display deadline with only date.
     * Day, Month Date Year
     * Example: Tuesday, April 1 2013
     */
    public static final String READABLE_DATEONLY_OUTPUT_FORMAT = "EEE, MMM dd yyyy";

    private final Date date;
    private final DeadlineType type;

    /**
     * Constructor for Deadline.
     */
    public Deadline(String dateString) throws IllegalValueException {
        assert dateString != null;
        // Trim and remove continuous whitespace
        String trimmedDateString = dateString.trim().replace(" +", " ");

        // Try date-only format
        if (DeadlineParser.isParsableDate(dateString)) {
            date = DeadlineParser.parseDateString(dateString);
            type = DeadlineType.DATEONLY;

        // Try time-only format
        } else if (DeadlineParser.isParsableTime(dateString)) {
            // The default date value to be assigned is today
            date = combineDateTime(new Date(), DeadlineParser.parseTimeString(dateString));
            type = DeadlineType.DATETIME;

        // Try complete date-time formats
        } else if (DeadlineParser.isParsableDateTime(dateString)) {
            date = DeadlineParser.parseDateTimeString(dateString);
            type = DeadlineType.DATETIME;

        } else {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }

    }

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public static boolean isValidDeadline(String dateString) {
        return DeadlineParser.isParsable(dateString);
    }

    /**
     * Combine date and time from 2 different sources.
     */
    public static Date combineDateTime(Date date, Date time) {
        return new Date(date.getYear(), date.getMonth(), date.getDate(),
                        time.getHours(), time.getMinutes());
    }

    @Override
    public String toString() {
        switch (type) {
        case DATETIME:
            return new SimpleDateFormat(READABLE_DATETIME_OUTPUT_FORMAT).format(date);

        case DATEONLY:
            return new SimpleDateFormat(READABLE_DATEONLY_OUTPUT_FORMAT).format(date);

        default:
            return date.toString();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Deadline // instanceof handles nulls
                    && this.date.equals(((Deadline) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
