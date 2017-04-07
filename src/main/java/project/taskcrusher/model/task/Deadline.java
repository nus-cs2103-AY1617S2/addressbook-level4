package project.taskcrusher.model.task;

import java.util.Date;
import java.util.Optional;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.shared.DateUtilApache;

//@@author A0163962X
/**
 * Represents a deadline for a task. Empty deadline means no deadline.
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Deadline provided must be a relative"
            + " or absolute date, and must not have passed";
    public static final String NO_DEADLINE = "";
    public static final boolean IS_LOADING_FROM_STORAGE = false;

    public final String deadline;

    /**
     * Creates a Deadline using the String passed
     *
     * @param deadline
     * @throws IllegalValueException
     */
    public Deadline(String deadline) throws IllegalValueException {
        assert deadline != null;

        if (deadline.equals(NO_DEADLINE)) {
            this.deadline = NO_DEADLINE;
        } else {
            this.deadline = DateUtilApache.dateAsStringForStorage(DateUtilApache.parseDate(deadline));
        }
    }

//    /**
//     * Creates a Deadline using the String passed. isNew means that this
//     * Deadline object is added from user input as opposed to being loaded from
//     * storage, and therefore the checking of whether the deadline date is in
//     * the past should NOT be bypassed.
//     *
//     * @param deadline
//     * @throws IllegalValueException
//     */
//    public Deadline(String deadline, boolean filterOverdue) throws IllegalValueException {
//        assert deadline != null;
//
//        if (deadline.equals(NO_DEADLINE)) {
//            this.deadline = NO_DEADLINE;
//        } else {
//            this.deadline = DateUtilApache.dateAsStringForStorage(DateUtilApache.parseDate(deadline, filterOverdue));
//        }
//
//    }

    /**
     * Returns Deadline in the form of Optional<Date>
     *
     * @return Deadline in the form of Optional<Date>, empty Optional<Date> if
     *         no deadline
     * @throws IllegalValueException
     */
    public Optional<Date> getDate() {

        Optional<Date> deadlineAsDate = Optional.empty();

        if (this.hasDeadline()) {
            try {
                return Optional.of(DateUtilApache.parseDate(this.deadline));
            } catch (IllegalValueException e) {
                // TODO this should not occur by default, provided that this
                // object was instantiated successfully.
                e.printStackTrace();
                return deadlineAsDate;
            }
        } else {
            return deadlineAsDate;
        }
    }

    public boolean hasDeadline() {
        return !deadline.equals(NO_DEADLINE);
    }

    public boolean isWithin(Timeslot timeslot) {
        assert timeslot != null;
        if (!hasDeadline()) {
            return false;
        }

        Date deadlineInDate = getDate().get();

        if ((deadlineInDate.after(timeslot.start)) && (deadlineInDate.before(timeslot.end))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (this.hasDeadline()) {
            return DateUtilApache.dateAsStringForStorage(this.getDate().get());
        } else {
            return Deadline.NO_DEADLINE;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                        && this.deadline.equals(((Deadline) other).deadline));
    }

}
