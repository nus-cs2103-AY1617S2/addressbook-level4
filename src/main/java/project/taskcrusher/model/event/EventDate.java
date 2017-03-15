package project.taskcrusher.model.event;

import project.taskcrusher.commons.exceptions.IllegalValueException;

/**
 * stores a timeslot, from {@code startDate} to {@code endDate}endDate
 */
public class EventDate {
    public static final String MESSAGE_EVENT_DATE_CONSTRAINTS = "";
    public static final String EVENT_DATE_VALIDATION_REGEX = "";
    //TODO: public static final Timer to check if valid eventdate

    //Note that these are public, and therefore no setters/getters
    public final String startDate;
    public final String endDate;

    public EventDate(String startDate, String endDate) throws IllegalValueException {
        assert startDate != null;
        assert endDate != null;
        if (!isValidEventDate(startDate, endDate)) {
            throw new IllegalValueException(MESSAGE_EVENT_DATE_CONSTRAINTS);
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private boolean isValidEventDate(String startDate, String endDate) {
        //TODO: is start > end
        //if start < currentTime
        //if invalid start, end format

        return false;
    }

    @Override
    public String toString() {
        return startDate + " " + endDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDate // instanceof handles nulls
                && this.startDate.equals(((EventDate) other).startDate)
                && this.endDate.equals(((EventDate) other).endDate)); // state check
    }

}
