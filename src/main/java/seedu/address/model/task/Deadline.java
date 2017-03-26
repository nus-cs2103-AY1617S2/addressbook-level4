package seedu.address.model.task;

import java.time.ZonedDateTime;
import java.util.Objects;

import seedu.address.model.task.exceptions.PastDateTimeException;

//@@author A0140023E
public class Deadline {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Deadline should not be in the past";

    private ZonedDateTime dateTime;

    public Deadline(ZonedDateTime dateTime) throws PastDateTimeException {
        assert dateTime != null;

        if (dateTime.isBefore(ZonedDateTime.now())) {
            throw new PastDateTimeException(MESSAGE_DEADLINE_CONSTRAINTS);
        }

        this.dateTime = dateTime;
    }

    public ZonedDateTime getValue() {
        return dateTime;
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final Deadline other = (Deadline) o;

        return Objects.equals(getValue(), other.getValue());
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
