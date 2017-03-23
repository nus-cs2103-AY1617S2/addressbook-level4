package seedu.address.model.person;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;

//@@author A0121668A
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

    public final LocalDate value;

    public LocalDate getValue() {
        return value;
    }

//@@author A0110491U A0121668A
    /**
     * Validates given ByDate.
     *
     * @throws IllegalValueException if given ByDate string is invalid.
     */
    public ByDate(String bydate) throws IllegalValueException {
        if (bydate == null) {
            this.value = null;
        } else {
            if (!isValidByDate(bydate)) {
                throw new IllegalValueException(MESSAGE_BYDATE_CONSTRAINTS);
            }
            this.value = StringUtil.parseStringToDate(bydate);
        }
    }

  //@@author A0110491U
    /**
     * Returns true if a given string is a valid deadline ByDate.
     */
    public static boolean isValidByDate(String test) {
        if (test == null) {
            return true;
        }
        Pattern pattern = Pattern.compile(BYDATE_VALIDATION_REGEX);
        Matcher matchers = pattern.matcher(test);
        if (test.matches(BYDATE_VALIDATION_REGEX)) {
            matchers.matches();
            String day = matchers.group(1);
            String month = matchers.group(2);
            boolean daytrue = Integer.parseInt(day) < 32;
            boolean monthtrue = (Integer.parseInt(month) < 13) && (Integer.parseInt(month) > 0);
            return daytrue && monthtrue;
        }
        return false;
    }

  //@@author
    @Override
    public String toString() {
        return value.toString();
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
