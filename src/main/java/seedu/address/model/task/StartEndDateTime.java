package seedu.address.model.task;

import java.time.ZonedDateTime;
import java.util.Objects;

import seedu.address.model.task.exceptions.InvalidDurationException;
import seedu.address.model.task.exceptions.PastDateTimeException;

//@@author A0140023E
public class StartEndDateTime {
    public static final String MESSAGE_STARTDATETIME_CONSTRAINTS = "Start Date/Time should not be in the past";
    public static final String MESSAGE_ENDDATETIME_CONSTRAINTS = "End Date/Time should not be in the past";
    public static final String MESSAGE_STARTENDDATETIME_CONSTRAINTS = "End Date/Time must be after Start Date/Time";

    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    /**
     * Constructs a new StartDateTime with the given start and end time.
     *
     * @param startDateTime the start DateTime
     * @param endDateTime the end DateTime
     * @throws PastDateTimeException if any of the DateTimes are before the current DateTime
     * @throws InvalidDurationException if the end DateTime is before or same as the start DateTime
     */
    public StartEndDateTime(ZonedDateTime startDateTime, ZonedDateTime endDateTime)
            throws PastDateTimeException, InvalidDurationException {
        this(startDateTime, endDateTime, false);
    }

    /**
     * Constructs a new StartEndDateTime with the given start and end time. Ignores checking for
     * dates in the past if {@code ignorePast} is true.
     *
     * @param startDateTime the start DateTime
     * @param endDateTime the end DateTime
     * @param allowPastDateTime ignore checking if DateTimes are in the past. Avoid setting to true except for cases
     *        such as loading from storage
     * @throws PastDateTimeException if any of the DateTimes are before the current DateTime
     * @throws InvalidDurationException if the end DateTime is before or same as the start DateTime
     */
    public StartEndDateTime(ZonedDateTime startDateTime, ZonedDateTime endDateTime, boolean allowPastDateTime)
            throws PastDateTimeException, InvalidDurationException {
        assert startDateTime != null && endDateTime != null;

        if (!allowPastDateTime) {
            // fix the current date time otherwise testing startDateTime might pass but
            // testing endDateTime might fail because the current DateTime might have passed endDateTime
            ZonedDateTime currentDateTime = ZonedDateTime.now();
            if (startDateTime.isBefore(currentDateTime)) {
                throw new PastDateTimeException(MESSAGE_STARTDATETIME_CONSTRAINTS);
            }

            if (endDateTime.isBefore(currentDateTime)) {
                throw new PastDateTimeException(MESSAGE_ENDDATETIME_CONSTRAINTS);
            }
        }

        // The end date time must be after the start date time, but not same or before
        if (!endDateTime.isAfter(startDateTime)) {
            throw new InvalidDurationException(MESSAGE_STARTENDDATETIME_CONSTRAINTS);
        }

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public String toString() {
        return "Start Date: " + startDateTime.toString() + " End Date: " + endDateTime.toString();
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
        final StartEndDateTime other = (StartEndDateTime) o;

        return Objects.equals(getStartDateTime(), other.getStartDateTime())
                && Objects.equals(getEndDateTime(), other.getEndDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, endDateTime);
    }

}
