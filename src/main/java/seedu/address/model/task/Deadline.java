package seedu.address.model.task;

import java.time.ZonedDateTime;
import java.util.Objects;

import seedu.address.model.task.exceptions.PastDateTimeException;

//@@author A0140023E
public class Deadline {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Deadline should not be in the past";

    private ZonedDateTime dateTime;

    /**
     * Constructs a new Deadline with the given DateTime.
     *
     * @param dateTime the DateTime to specify for the deadline
     * @throws PastDateTimeException if {@code dateTime} is before the current DateTime
     */
    public Deadline(ZonedDateTime dateTime) throws PastDateTimeException {
        this(dateTime, false);
    }

    /**
     * Constructs a new Deadline with the given DateTime. Ignores checking for dates in the past if
     * {@code ignorePast} is true.
     *
     * @param dateTime the DateTime to specify for the deadline
     * @param allowPastDateTime ignore checking if {@code dateTime} is in the past. Avoid setting
     *        to true except for cases such as loading from storage
     * @throws PastDateTimeException if {@code dateTime} is before the current DateTime and
     *         {@code allowPastDateTime} is {@code false}
     */
    public Deadline(ZonedDateTime dateTime, boolean allowPastDateTime) throws PastDateTimeException {
        assert dateTime != null;

        if (!allowPastDateTime) {
            if (dateTime.isBefore(ZonedDateTime.now())) {
                throw new PastDateTimeException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
        }

        this.dateTime = dateTime;
    }

    public ZonedDateTime getDateTime() {
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

        return Objects.equals(getDateTime(), other.getDateTime());
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
