package seedu.onetwodo.model.task;


import seedu.onetwodo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time in the toDo list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "If task date exists, it must be a valid date";
    public static final String DATE_VALIDATION_REGEX = ".*";

    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date toDo string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        String trimmedDate = date.trim();
        
        if(trimmedDate.isEmpty()) {
            this.value = trimmedDate;
        } else {
            // if user has input some date, use Natty to parse it
            
            // parse input into Natty
            String displayableDate = "";
            
            if (!isValidDate(trimmedDate)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }
            this.value = displayableDate;
        }
    }

    /**
     * Returns if a given string is a valid task date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
