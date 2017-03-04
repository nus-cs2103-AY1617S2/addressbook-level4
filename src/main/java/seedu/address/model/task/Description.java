package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

public class Description {
    
    /*
     * The first character of the description must not be a whitespace, otherwise
     * " " (a blank string) becomes a valid input.
     */
   // public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String desc;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException
     *             if given description string is invalid.
     */
    public Description(String desc) throws IllegalValueException {
        assert desc != null;
        String trimmedDesc = desc.trim();

        // if (!isValidName(trimmedName)) {
        // throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        // }
        this.desc = trimmedDesc;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidDescription(String desc) {
        //return test.matches(NAME_VALIDATION_REGEX);
        return true;
    }

    @Override
    public String toString() {
        return desc;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                        && this.desc.equals(((Description) other).desc)); // state
                                                                           // check
    }

    @Override
    public int hashCode() {
        return desc.hashCode();
    }

}
