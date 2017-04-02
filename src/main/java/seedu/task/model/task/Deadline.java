//@@author A0144813J
package seedu.task.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 *
 */
public class Deadline {
    public static final String MESSAGE_DATE_CONSTRAINTS = "The deadline entered cannot be recognized.\n"
            + "Task deadline should be alphanumeric with "
            + "forward slashes(/), "
            + "dashes(-), and/or "
            + "coma(,).\n";
    public static final String MESSAGE_DATE_NOT_FOUND =
              "The date entered is either not recognized or not a future date.\n"
            + "Please paraphrase it or choose another date.";
    public static final String DATE_VALIDATION_REGEX = "[\\s | [a-zA-Z0-9,/:-]]+";
    public static final String DATE_FORMAT = "EEE, MMM d yyyy HH:mm";
    public static final String DATE_TYPE_FLOATING = "floating";
    public static final String DATE_STRING_DEFAULT_VALUE = DATE_TYPE_FLOATING;

    public final String value;
    private final List<DateGroup> parsedDeadline;

    private static PrettyTimeParser parser = new PrettyTimeParser();
    private static SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);


    /**
     * Validates given deadline value.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        assert deadline != null;
        String trimmedDeadline = deadline.trim();
        this.parsedDeadline = parseDeadline(trimmedDeadline);
        if (!isValidDate(trimmedDeadline)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        if (trimmedDeadline.equals("floating") || parsedDeadline.get(0).isRecurring()) {
            this.value = trimmedDeadline;
        } else {
            if (isFromTo()) {
                this.value = "From " + format(fromDeadline()) + " to " + format(toDeadline());
            } else {
                this.value = format(nextDeadline());
            }
        }
    }

    /**
     * Returns true if the given deadline String is a valid date.
     */
    public static boolean isValidDate(String deadline) {
        boolean isRegexMatched = deadline.matches(DATE_VALIDATION_REGEX);
        boolean isDeadlineFound = parser.parse(deadline).size() != 0 || deadline.equals("floating");
        return isRegexMatched && isDeadlineFound;
    }

    /**
     * Returns a List<DateGroup> object for the given deadline string.
     */
    public static List<DateGroup> parseDeadline(String deadline) {
        List<DateGroup> deadlines = parser.parseSyntax(deadline);
        return deadlines;
    }

    /**
     * Returns a formatted String of the given Date object.
     */
    public static String format(Date date) {
        if (date != null) {
            return formatter.format(date);
        }
        return "";
    }

    /**
     * Returns true if the given date coincides with this Deadline.
     */
    public boolean isSameDate(Date date) {
        if (isFloating()) {
            return false;
        }
        if (isFromTo()) {
            return DateUtils.truncatedCompareTo(fromDeadline(), date, 5)
                    * DateUtils.truncatedCompareTo(date, toDeadline(), 5)
                    >= 0;
        } else {
            return DateUtils.truncatedEquals(date, nextDeadline(), 5);
        }
    }

    /**
     * Returns true if the given deadline is recurring.
     */
    public boolean isRecurring() {
        if (parsedDeadline.size() != 0) {
            return parsedDeadline.get(0).isRecurring();
        }
        return false;
    }

    /**
     * Returns true if the deadline type is floating.
     */
    public boolean isFloating() {
        return this.value.equals(DATE_TYPE_FLOATING);
    }

    /**
     * Returns true if the object is From - To.
     */
    public boolean isFromTo() {
        if (this.parsedDeadline.size() == 0) {
            return false;
        }
        return this.parsedDeadline.get(0).getDates().size() == 2;
    }

    /**
     * Returns the next Deadline.
     */
    public Date nextDeadline() {
        if (parsedDeadline.size() != 0) {
            return parsedDeadline.get(0).getDates().get(0);
        }
        return new Date();
    }

    /**
     * Returns true if this deadline is past.
     */
    public boolean isOverdue() {
        return (new Date()).after(toDeadline());
    }

    /**
     * Returns the From date object.
     */
    public Date fromDeadline() {
        if (parsedDeadline.size() != 0) {
            return parsedDeadline.get(0).getDates().get(0);
        }
        return new Date();
    }

    /**
     * Returns the To date object.
     * If the parsedDeadline object contains 2 Date objects, returns the second one,
     * else if there is only 1 Date object, returns it. When no Date objects found,
     * return a new Date object.
     */
    public Date toDeadline() {
        if (parsedDeadline.size() != 0) {
            if (parsedDeadline.size() == 2) {
                return parsedDeadline.get(0).getDates().get(1);
            }
            return parsedDeadline.get(0).getDates().get(0);
        }
        return new Date();
    }

    @Override
    public String toString() {
        if (value.equals("floating") || isFromTo()) {
            return value;
        }
        return formatter.format(parsedDeadline.get(0).getDates().get(0));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.toString().equals(((Deadline) other).toString())); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
