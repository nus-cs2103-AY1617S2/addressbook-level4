package savvytodo.model.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.commons.util.DateTimeUtil;
import savvytodo.commons.util.StringUtil;

/**
 * @author A0140016B
 *
 * Represents Task's DateTime in the task manager Guarantees: immutable;
 * is valid as declared in {@link #isValidDateTime(String, String)} *
 */
public class DateTime implements Comparable<DateTime> {

    public String startValue;
    public String endValue;

    private LocalDateTime start;
    private LocalDateTime end;

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Start date/time should not be after End date/time";

    private static final int COMPARE_TO_SMALLER = -1;
    private static final int COMPARE_TO_EQUAL = 0;
    private static final int COMPARE_TO_GREATER = 1;

    public static final String DATETIME_STRING_CONNECTOR = " = ";
    private static final String DATETIME_STRING_TO_STRING_CONNECTOR = " ~ ";
    public static final String[] DEFAULT_VALUES = {StringUtil.EMPTY_STRING, StringUtil.EMPTY_STRING};

    /**
     * Default constructor
     * @throws DateTimeException
     */
    public DateTime() throws IllegalValueException {
        this(DEFAULT_VALUES);
    }

    /**
     * Validates given DateTime.
     * If DateTime is NULL, means that the task is not an event
     * @throws IllegalValueException end date occurring before start date.
     */
    public DateTime(String startDateTime, String endDateTime) throws IllegalValueException {
        if (endDateTime != null && !endDateTime.isEmpty()) {
            this.end = LocalDateTime.parse(endDateTime.trim(), DateTimeUtil.DATE_FORMATTER);
            this.endValue = this.end.format(DateTimeUtil.DATE_STRING_FORMATTER);
        } else {
            this.endValue = endDateTime;
        }
        if (startDateTime != null && !startDateTime.isEmpty()) {
            this.start = LocalDateTime.parse(startDateTime.trim(), DateTimeUtil.DATE_FORMATTER);
            this.startValue = this.start.format(DateTimeUtil.DATE_STRING_FORMATTER);
            if (!isValidDateTime(this.start, this.end)) {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            }
        } else {
            this.startValue = startDateTime;
        }

    }

    public DateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        assert (startDateTime != null && endDateTime != null);
        this.start = startDateTime;
        this.end = endDateTime;
        this.endValue = this.end.format(DateTimeUtil.DATE_STRING_FORMATTER);
        this.startValue = this.start.format(DateTimeUtil.DATE_STRING_FORMATTER);
    }

    /**
     * Constructor when given input as String array
     * @param dateTime
     * @throws IllegalValueException
     * @throws DateTimeException
     */
    public DateTime(String[] dateTime) throws DateTimeException, IllegalValueException {
        this(dateTime[0], dateTime[1]);
    }

    /**
     * Returns true if a given string is a valid task dateTime.
     */
    public static boolean isValidDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime != null && endDateTime != null) {
            return (startDateTime.isBefore(endDateTime));
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (this.start != null && this.end != null) {
            return startValue + DATETIME_STRING_TO_STRING_CONNECTOR + endValue;
        } else if (this.end != null) {
            return endValue;
        } else {
            return StringUtil.EMPTY_STRING;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                        && this.toString()
                                .equals(((DateTime) other).toString())); // state check
    }

    @Override
    public int compareTo(DateTime o) {
        if (this.start == null && this.end == null) {
            if (o.start == null && o.end == null) {
                return COMPARE_TO_EQUAL;
            } else {
                return COMPARE_TO_SMALLER;
            }
        }

        if (this.start == null && this.end != null) {
            if (o.start != null && o.end != null) {
                return this.end.compareTo(o.start);
            } else if (o.end != null) {
                return this.end.compareTo(o.end);
            } else {
                return COMPARE_TO_GREATER;
            }
        }

        if (this.start != null && this.end != null) {
            if (o.start != null && o.end != null) {
                int result = this.start.compareTo(o.start);
                if (result == COMPARE_TO_EQUAL) {
                    return this.end.compareTo(o.end);
                } else {
                    return result;
                }
            } else if (o.start == null && o.end != null) {
                return this.start.compareTo(o.end);
            } else {
                return COMPARE_TO_GREATER;
            }
        }

        return COMPARE_TO_EQUAL;
    }

    /**
     * Set method for start
     * @param LocalDateTime
     */
    public void setStart(LocalDateTime startDateTime) {
        this.start = startDateTime;
    }

    /**
     * Set method for end
     * @param LocalDateTime
     */
    public void setEnd(LocalDateTime endDateTime) {
        this.end = endDateTime;
    }

}
