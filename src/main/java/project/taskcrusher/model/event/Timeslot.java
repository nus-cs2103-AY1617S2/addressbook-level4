package project.taskcrusher.model.event;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.joestelmach.natty.DateGroup;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.shared.DateUtil;

//@@author A0163962X
/**
 * Represents a timeslot for an event from {@code start} to {@code end}
 */
public class Timeslot implements Comparable<Timeslot> {

    public static final String MESSAGE_TIMESLOT_RANGE = "Start date must be before end date";
    public static final String MESSAGE_TIMESLOT_CLASH = "Timeslot clashes with one or more pre-existing events";
    public static final String MESSAGE_TIMESLOT_DNE = "One or more timeslots must be provided";
    public static final String MESSAGE_TIMESLOT_PAIRS = "Timeslot must contain pair of dates, "
            + "or if you intended to input a single date, it is invalid";

    public static final boolean IS_LOADING_FROM_STORAGE = false;
    public static final String NO_TIMESLOT = "";

    public Date start;
    public Date end;

    public static Timeslot constructTimeslotFromEndDate(String end) throws IllegalValueException {
        return new Timeslot(end);
    }

    private Timeslot(String end) throws IllegalValueException {
        assert end != null;

        this.start = new Date();
        this.end = DateUtil.parseDate(end);

        if (!isValidTimeslot(this.start, this.end)) {
            throw new IllegalValueException(MESSAGE_TIMESLOT_RANGE);
        }
    }

    public Timeslot(String start, String end) throws IllegalValueException {
        assert start != null;
        assert end != null;

        this.start = DateUtil.parseDate(start);
        DateGroup startInfo = DateUtil.parseDateAsDateGroup(start);

        this.end = DateUtil.parseDate(end);
        DateGroup endInfo = DateUtil.parseDateAsDateGroup(end);

        // special cases:
        // handle whole day events
        if (startInfo.isTimeInferred() && start.compareTo(end) == 0) {
            this.start = DateUtils.setHours(this.start, 0);
            this.start = DateUtils.setMinutes(this.start, 0);
            this.start = DateUtils.setSeconds(this.start, 0);
            this.start = DateUtils.setMilliseconds(this.start, 0);
            this.end = DateUtils.setHours(this.end, 23);
            this.end = DateUtils.setMinutes(this.end, 59);
            this.end = DateUtils.setSeconds(this.end, 59);
            this.end = DateUtils.setMilliseconds(this.end, 59);
            // handle date omission after "to"
        } else if (endInfo.isDateInferred()) {
            Date tempDate = (Date) this.start.clone();
            long secondsFromMidnight = DateUtils.getFragmentInSeconds(this.end, Calendar.DATE);
            tempDate = DateUtils.setHours(tempDate, 0);
            tempDate = DateUtils.setMinutes(tempDate, 0);
            tempDate = DateUtils.setSeconds(tempDate, 0);
            tempDate = DateUtils.setMilliseconds(tempDate, 0);
            tempDate = DateUtils.addSeconds(tempDate, (int) secondsFromMidnight);
            this.end = tempDate;
        }

        if (!isValidTimeslot(this.start, this.end)) {
            throw new IllegalValueException(MESSAGE_TIMESLOT_RANGE);
        }
    }

    // REMOVED FOR PAST DATES
    // public Timeslot(String start, String end, boolean isNew) throws
    // IllegalValueException {
    // assert start != null;
    // assert end != null;
    //
    // this.start = DateUtilApache.parseDate(start, isNew);
    // this.end = DateUtilApache.parseDate(end, isNew);
    //
    // if (!isValidTimeslot(this.start, this.end, isNew)) {
    // throw new IllegalValueException(MESSAGE_TIMESLOT_RANGE);
    // }
    // }

    /**
     * Checks if {@code another} has overlapping timeslot with this Timeslot
     * object.
     *
     * @param another
     * @return true if overlapping, false otherwise.
     */
    public boolean isOverlapping(Timeslot another) {
        assert another != null;
        if (start.equals(another.start) || end.equals(another.end)) {
            return true;
        } else if (start.before(another.start) && end.after(another.start)) {
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

    // private boolean isValidTimeslot(Date start, Date end, boolean isNew) {
    //
    // if (!isNew) {
    // return true;
    // } else if (!end.before(start)) {
    // return true;
    // } else {
    // return false;
    // }
    // }

    @Override
    public String toString() {
        return DateUtil.dateAsStringForStorage(start) + " to " + DateUtil.dateAsStringForStorage(end);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        } else if (other instanceof Timeslot) {
            String thisTimeslotStripSeconds = toString();
            String otherTimeslotStripSeconds = ((Timeslot) other).toString();
            return thisTimeslotStripSeconds.equals(otherTimeslotStripSeconds);
        } else {
            return false;
        }
    }

    public int compareTo(Timeslot another) {
        return this.start.compareTo(another.start);
    }

}
