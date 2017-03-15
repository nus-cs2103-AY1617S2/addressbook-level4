package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's content in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidContent(String)}
 */
public class Content {

    public static final String MESSAGE_CONTENT_CONSTRAINTS =
            "Task content should only contain alphanumeric characters and spaces";

    /*
     * The first character of the task must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CONTENT_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullContent;

    /**
     * Validates given content.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Content(String content) throws IllegalValueException {
        String trimmedContent = content.trim();
        if (!isValidContent(trimmedContent)) {
            throw new IllegalValueException(MESSAGE_CONTENT_CONSTRAINTS);
        }
        this.fullContent = trimmedContent;
    }

    public boolean isThereContent() {
        return !fullContent.equals("");
    }
    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidContent(String test) {
        if (test.equals("")) {
            return true;
        }
        return test.matches(CONTENT_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullContent;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Content // instanceof handles nulls
                && this.fullContent.equals(((Content) other).fullContent)); // state check
    }

    @Override
    public int hashCode() {
        return fullContent.hashCode();
    }

}
