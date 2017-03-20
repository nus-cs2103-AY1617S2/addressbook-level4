package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

public class Description {

    /**
     * Represents a Task's Description in the to-do list. Guarantees: immutable;
     * is valid as declared in {@link #isValidDescription(String)}
     */

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Description can contain any characters";
    public static final String DESCRIPTION_VALIDATION_REGEX = "^\\w+(.+)?";

    public final String value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException
     *             if given start time string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        assert description != null;
        if (description.isEmpty()) {
            this.value = description;
        } else {
            String trimmedDescription = description.trim();
            if (!isValidDescription(trimmedDescription)) {
                throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
            }
            this.value = trimmedDescription;
        }
    }

    /**
     * Returns true if a given string is a valid task start time.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                        && this.value.equals(((Description) other).value)); // state
        // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
