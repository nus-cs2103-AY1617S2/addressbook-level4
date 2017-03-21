package project.taskcrusher.model.event;

import java.util.Date;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.shared.DateUtil;

/**
 * Represents a timeslot for an event from {@code start} to {@code end}
 */
public class Timeslot {

    public static final String MESSAGE_TIMESLOT_CONSTRAINTS = "Start date must be before end date";
    public static final String MESSAGE_TIMESLOT_CLASH = "Timeslot clashes with preexisting event";

    public final Date start;
    public final Date end;

    public Timeslot(String start, String end) throws IllegalValueException {
        assert start != null;
        assert end != null;

        this.start = DateUtil.parseDate(start, true);
        this.end = DateUtil.parseDate(end, true);

        if (!isValidTimeslot(this.start, this.end)) {
            throw new IllegalValueException(MESSAGE_TIMESLOT_CONSTRAINTS);
        }

//      TODO in model API
//        else if (isClashing(this)) {
//
//        }
    }

    public Timeslot(String start, String end, boolean isNew) throws IllegalValueException {
        assert start != null;
        assert end != null;

        this.start = DateUtil.parseDate(start, isNew);
        this.end = DateUtil.parseDate(end, isNew);

        if (!isValidTimeslot(this.start, this.end, isNew)) {
            throw new IllegalValueException(MESSAGE_TIMESLOT_CONSTRAINTS);
        }

//      TODO in model API
//        else if (isClashing(this)) {
//
//        }
    }

    private boolean isValidTimeslot(Date start, Date end) {
        if (!end.before(start)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidTimeslot(Date start, Date end, boolean isNew) {

        if (!isNew) {
            return true;
        } else if (!end.before(start)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return DateUtil.dateAsString(start) + " to " + DateUtil.dateAsString(end);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timeslot // instanceof handles nulls
                && this.start.equals(((Timeslot) other).start)
                && this.end.equals(((Timeslot) other).end)); // state check
    }

}
