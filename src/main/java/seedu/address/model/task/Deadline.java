package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * @author ryuus
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
            this.value = format(nextDeadline());
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
     * Returns true if the given deadline is recurring.
     */
    public boolean isRecurring() {
        if (parsedDeadline.size() != 0) {
            return parsedDeadline.get(0).isRecurring();
        }
        return false;
    }

    /**
     * Returns the next Deadline.
     */
    public Date nextDeadline() {
        if (parsedDeadline.size() != 0) {
            return parsedDeadline.get(0).getDates().get(0);
        }
        return null;
    }

    @Override
    public String toString() {
        if (value.equals("floating")) {
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
