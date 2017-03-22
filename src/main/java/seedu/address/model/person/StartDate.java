package seedu.address.model.person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents an Event's StartDate in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS =
            "Event StartDate can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)";

    /*
     * must be in digits only
     *
     */
    public static final String STARTDATE_VALIDATION_REGEX = "([0123][\\d])([01][\\d])([\\d][\\d])";

    public final String value;

    /**
     * Validates given StartDate.
     *
     * @throws IllegalValueException if given StartDate string is invalid.
     */
    public StartDate(String startdate) throws IllegalValueException {
        if (startdate == null) {
            this.value = null;
        } else {
            if (!isValidStartDate(startdate)) {
                throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
            }
            this.value = startdate;
        }
    }

    /**
     * Returns true if a given string is a valid event StartDate.
     */
    public static boolean isValidStartDate(String test) {
        if (test == null) {
            return true;
        }
        Pattern pattern = Pattern.compile(STARTDATE_VALIDATION_REGEX);
        Matcher matchers = pattern.matcher(test);
        if (test.matches(STARTDATE_VALIDATION_REGEX)) {
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
                || (other instanceof StartDate // instanceof handles nulls
                && this.value.equals(((StartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
