package project.taskcrusher.model.event;

import java.util.Date;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.shared.DateUtilApache;

//@@author A0163962X
/**
 * Represents a timeslot for an event from {@code start} to {@code end}
 */
public class Timeslot {

    public static final String MESSAGE_TIMESLOT_RANGE = "Start date must be before end date";
    public static final String MESSAGE_TIMESLOT_CLASH = "Timeslot clashes with one or more pre-existing events";
    public static final String MESSAGE_TIMESLOT_DNE = "One or more timeslots must be provided";
    public static final String MESSAGE_TIMESLOT_PAIRS = "Timeslot must contain pair of dates, "
            + "or if you intended to input a single date, it is invalid";

    public static final boolean IS_LOADING_FROM_STORAGE = false;
    public static final String NO_TIMESLOT = "";

    public final Date start;
    public final Date end;

    public static Timeslot constructTimeslotFromEndDate(String end) throws IllegalValueException {
        return new Timeslot(end);
    }

    private Timeslot(String end) throws IllegalValueException {
        assert end != null;

        this.start = new Date();
        this.end = DateUtilApache.parseDate(end, true);

        if (!isValidTimeslot(this.start, this.end)) {
            throw new IllegalValueException(MESSAGE_TIMESLOT_RANGE);
        }
    }

    public Timeslot(String start, String end) throws IllegalValueException {
        assert start != null;
        assert end != null;

        this.start = DateUtilApache.parseDate(start, true);
        this.end = DateUtilApache.parseDate(end, true);

        if (!isValidTimeslot(this.start, this.end)) {
            throw new IllegalValueException(MESSAGE_TIMESLOT_RANGE);
        }
    }

    public Timeslot(String start, String end, boolean isNew) throws IllegalValueException {
        assert start != null;
        assert end != null;

        this.start = DateUtilApache.parseDate(start, isNew);
        this.end = DateUtilApache.parseDate(end, isNew);

        if (!isValidTimeslot(this.start, this.end, isNew)) {
            throw new IllegalValueException(MESSAGE_TIMESLOT_RANGE);
        }
    }

    /**
     * Checks if {@code another} has overlapping timeslot with this Timeslot
     * object.
     *
     * @param another
     * @return true if overlapping, false otherwise.
     */
    public boolean isOverlapping(Timeslot another) {
        assert another != null;
        if (start.before(another.start) && end.after(another.start)) {
            return true;
        } else if (start.before(another.end) && end.after(another.end)) {
            return true;
        } else if (start.after(another.start) && start.before(another.end)) {
            return true;
        } else if (start.compareTo(another.start) == 0 && end.compareTo(another.end) == 0) {
            return true;
        } else {
            return false;
        }
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
        return DateUtilApache.dateAsStringForStorage(start) + " to " + DateUtilApache.dateAsStringForStorage(end);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timeslot // instanceof handles nulls
                        && this.start.equals(((Timeslot) other).start)
                        && this.end.equals(((Timeslot) other).end)); // state check
    }

}
