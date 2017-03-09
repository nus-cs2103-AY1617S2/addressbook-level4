package seedu.address.model.task;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import seedu.address.commons.exceptions.IllegalValueException;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

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
    public static final String DATE_FORMAT = "EEE, MMM d yyyy";

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
        if (!isValidDate(trimmedDeadline)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        parsedDeadline = parseDeadline(trimmedDeadline);
        
        this.value = toString();
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
     * Returns true if the given deadline is recurring.
     */
    public boolean isRecurring() {
    	return parsedDeadline.get(0).isRecurring();
    }

    @Override
    public String toString() {
    	if (parsedDeadline.size() == 0) {
    		return "floating";
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
