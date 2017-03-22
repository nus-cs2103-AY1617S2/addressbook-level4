package seedu.address.model.person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents an Event's EndDate in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDate(String)}
 */
public class EndDate {

    public static final String MESSAGE_ENDDATE_CONSTRAINTS =
            "Event EndDate can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)";

    /*
     * must be in digits only
     *
     */
    public static final String ENDDATE_VALIDATION_REGEX = "([0123][\\d])([01][\\d])([\\d][\\d])";

    public final String value;

    /**
     * Validates given EndDate.
     *
     * @throws IllegalValueException if given EndDate string is invalid.
     */
    public EndDate(String enddate) throws IllegalValueException {
        if (enddate == null) {
            this.value = null;
        } else {
            if (!isValidEndDate(enddate)) {
                throw new IllegalValueException(MESSAGE_ENDDATE_CONSTRAINTS);
            }
            this.value = enddate;
        }
    }

    /**
     * Returns true if a given string is a valid event EndDate.
     */
    public static boolean isValidEndDate(String test) {
        if (test == null) {
            return true;
        }
        Pattern pattern = Pattern.compile(ENDDATE_VALIDATION_REGEX);
        Matcher matchers = pattern.matcher(test);
        if (test.matches(ENDDATE_VALIDATION_REGEX)) {
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
                || (other instanceof EndDate // instanceof handles nulls
                && this.value.equals(((EndDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
