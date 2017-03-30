package seedu.onetwodo.model.task;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.onetwodo.commons.exceptions.IllegalValueException;

//@@author A0139343E
/**
 * Represents a Task's date in the toDo list. Guarantees: immutable.
 */
public class Date {

    public static final String MESSAGE_DATE_INPUT_CONSTRAINTS = "Invalid date input. "
            + "Please check the format in user guide or help command.";

    public String value; // value to be displayed to user
    protected Optional<LocalDateTime> localDateTime;

    // to be used if no date OR time is specified.
    protected LocalDateTime defaultDateTime = LocalDateTime.now();

    /**
     * Create a date.
     *
     * @throws IllegalValueException
     *             if given date toDo string is invalid.
     */
    public Date(String input) throws IllegalValueException {
        assert input != null;
    }

    public Date(LocalDateTime localDateTime) {
        this.localDateTime = Optional.of(localDateTime);
    }

    /**
     *
     * @param localDateTime
     *            input optional LocalDateTime
     * @return String to be displayed to user.
     */
    public String createDisplayValue(Optional<LocalDateTime> localDateTime) {
        if (!localDateTime.isPresent()) {
            this.value = "";
            return value;
        } else {
            this.value = localDateTime.get().toString();
            return value;
        }
    }

    public Date getDate() {
        return this;
    }

    public boolean hasDate() {
        return this.localDateTime.isPresent();
    }

    @Override
    public String toString() {
        return value;
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

    public LocalDateTime getLocalDateTime() throws NoSuchElementException {
        return localDateTime.get();
    }

    public void setLocalDateTime(LocalDateTime newLocalDateTime) throws NoSuchElementException {
        this.localDateTime = Optional.of(newLocalDateTime);
    }

}
