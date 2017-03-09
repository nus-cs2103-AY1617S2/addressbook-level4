package seedu.typed.model.task;
import java.util.Optional;


/**
 * Represents a Task's date number in the task manager. Guarantees: immutable;
 * A task date is optional.
 */
public class Date {

    public final Optional<String> value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException
     *             if given date string is invalid.
     */
    public Date(Optional<String> date) {
        this.value = date;
    }
    /**
     * Returns the date if exists
     * else returns an empty string
     */
    @Override
    public String toString() {
        return value.orElse("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                        && this.value.equals(((Date) other).value)); // state
        // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
