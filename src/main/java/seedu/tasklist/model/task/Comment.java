package seedu.tasklist.model.task;

import java.util.Optional;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's comment in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidComment(String)}
 */
public class Comment {

    public static final String MESSAGE_COMMENT_CONSTRAINTS =
            "Task comments can take any values, and it should not be blank";

    /*
     * The first character of the comment must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String COMMENT_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given comment.
     *
     * @throws IllegalValueException if given comment string is invalid.
     */
    public Comment(String comment) throws IllegalValueException {
        assert comment != null;
        if (!isValidComment(comment)) {
            throw new IllegalValueException(MESSAGE_COMMENT_CONSTRAINTS);
        }
        this.value = comment;
    }

    //@@Author A0143355J
    public Comment(Optional<String> comment) throws IllegalValueException {
        if (isCommentPresent(comment)) {
            String commentString = comment.get();
            assert commentString != null;
            if (!isValidComment(commentString)) {
                throw new IllegalValueException(MESSAGE_COMMENT_CONSTRAINTS);
            }
            this.value = commentString;
        } else {
            this.value = "NIL";
        }
    }

    //@@Author A0143355J
    public boolean isCommentPresent(Optional<String> comment) {
        return comment.isPresent();
    }

    /**
     * Returns true if a given string is a valid Task email.
     */
    public static boolean isValidComment(String test) {
        return test.matches(COMMENT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Comment // instanceof handles nulls
                && this.value.equals(((Comment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
