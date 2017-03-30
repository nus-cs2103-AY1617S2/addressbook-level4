package project.taskcrusher.model.shared;

import project.taskcrusher.commons.exceptions.IllegalValueException;

//@@author A0163962X
/**
 * Represents a Task's description. Guarantees: immutable; is valid as declared
 * in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Descriptions can take any value, "
            + "but must not be blank";

    public static final String NO_DESCRIPTION = "";
    public static final String DESCRIPTION_VALIDATION_REGEX = "\\S.*";

    public final String description;

    /**
     * Creates a Description using the String passed
     *
     * @throws IllegalValueException if given description string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        assert description != null;

        if (description.equals(NO_DESCRIPTION)) {
            this.description = NO_DESCRIPTION;
        } else if (isValidDescription(description)) {
            this.description = description;
        } else {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return this.description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                        && this.description.equals(((Description) other).description)); // state
        // check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    /**
     * Checks whether a description is valid
     *
     * @param description
     * @return true if description exists and contains at least one
     *         non-whitespace character
     */
    public static boolean isValidDescription(String description) {
        return description.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    public boolean hasDescription() {
        return !description.equals(NO_DESCRIPTION);
    }

}
