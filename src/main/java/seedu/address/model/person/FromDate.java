package seedu.address.model.person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents an Event's FromDate in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidFromDate(String)}
 */
public class FromDate {

    public static final String MESSAGE_FROMDATE_CONSTRAINTS =
            "Event FromDate can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)";

    /*
     * must be in digits only
     *
     */
    public static final String FROMDATE_VALIDATION_REGEX = "([0123][\\d])([01][\\d])([\\d][\\d])";

    public final String value;

    /**
     * Validates given FromDate.
     *
     * @throws IllegalValueException if given FromDate string is invalid.
     */
    public FromDate(String fromdate) throws IllegalValueException {
        if (fromdate == null) {
            this.value = null;
        } else {
            if (!isValidFromDate(fromdate)) {
                throw new IllegalValueException(MESSAGE_FROMDATE_CONSTRAINTS);
            }
            this.value = fromdate;
        }
    }

    /**
     * Returns true if a given string is a valid event FromDate.
     */
    public static boolean isValidFromDate(String test) {
        if (test == null) {
            return true;
        }
        Pattern pattern = Pattern.compile(FROMDATE_VALIDATION_REGEX);
        Matcher matchers = pattern.matcher(test);
        if (test.matches(FROMDATE_VALIDATION_REGEX)) {
            matchers.matches();
            String day = matchers.group(1);
            String month = matchers.group(2);
            boolean daytrue = Integer.parseInt(day) < 32;
            boolean monthtrue = (Integer.parseInt(month) < 13) && (Integer.parseInt(month) > 0);
            return daytrue && monthtrue;
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FromDate // instanceof handles nulls
                && this.value.equals(((FromDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
