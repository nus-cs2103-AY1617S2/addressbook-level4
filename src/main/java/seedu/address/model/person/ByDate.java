package seedu.address.model.person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents a Deadline's ByDate in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidByDate(String)}
 */
public class ByDate {

    public static final String MESSAGE_BYDATE_CONSTRAINTS =
            "Deadline ByDate can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)";

    /*
     * must be in digits only
     *
     */
    public static final String BYDATE_VALIDATION_REGEX = "([0123][\\d])([01][\\d])([\\d][\\d])";

    public final String value;

    /**
     * Validates given ByDate.
     *
     * @throws IllegalValueException if given ByDate string is invalid.
     */
    public ByDate(String bydate) throws IllegalValueException {
        assert bydate != null;
        if (!isValidByDate(bydate)) {
            throw new IllegalValueException(MESSAGE_BYDATE_CONSTRAINTS);
        }
        this.value = bydate;
    }

    /**
     * Returns true if a given string is a valid deadline ByDate.
     */
    public static boolean isValidByDate(String test) {
        Pattern pattern = Pattern.compile(BYDATE_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(test);
        boolean day = Integer.parseInt(matcher.group(1)) < 32;
        boolean month = Integer.parseInt(matcher.group(2)) < 13;
        return (test.matches(BYDATE_VALIDATION_REGEX) && day && month);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ByDate // instanceof handles nulls
                && this.value.equals(((ByDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
