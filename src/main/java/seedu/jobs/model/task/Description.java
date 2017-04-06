package seedu.jobs.model.task;

import java.util.Optional;

import seedu.jobs.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's description in JOBS.
 */
//@@author A0130979U
public class Description {

    public static final String DEFAULT_DESCRIPTION = "";
    public static final String MESSAGE_DESCRIPTION_CONSTRAINT = "Description cannot be more than 150 characters";
    public static final int DESCRIPTION_LENGTH_CONSTRAINT = 150;
    public final String value;

    public Description(Optional<String> description) throws IllegalValueException {
        if (!description.isPresent()) {
            this.value = DEFAULT_DESCRIPTION;
        } else {
            if (!isValidDescription(description.get())) {
                throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINT);
            }
            this.value = description.get();
        }
    }

    public static boolean isValidDescription(String description) {
        return description.length() <= DESCRIPTION_LENGTH_CONSTRAINT;
    }

    @Override
    public String toString() {
        return this.value;
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

