package project.taskcrusher.model.task;

import java.util.Date;
import java.util.Optional;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.shared.DateUtil;

/**
 * Represents a deadline for a task. Empty deadline means no deadline.
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Deadline provided must be a relative" +
        " or absolute date, and must not have passed";
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
            this.deadline = DateUtil.dateAsString(DateUtil.parseDate(deadline, true));
        }
    }

    /**
     * Creates a Deadline using the String passed. isNew means that this
     * Deadline object is added from user input as opposed to being loaded from
     * storage, and therefore the checking of whether the deadline date is in
     * the past should NOT be bypassed.
     *
     * @param deadline
     * @throws IllegalValueException
     */
    public Deadline(String deadline, boolean isNew) throws IllegalValueException {
        assert deadline != null;

        if (deadline.equals(NO_DEADLINE)) {
            this.deadline = NO_DEADLINE;
        } else {
            this.deadline = DateUtil.dateAsString(DateUtil.parseDate(deadline, isNew));
        }

    }

    @Override
    public String toString() {
        if (this.hasDeadline()) {
            return DateUtil.dateAsString(this.getDate().get());
        } else {
            return Deadline.NO_DEADLINE;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                        // TODO: change it so that it deals with Optional
                        && this.deadline.equals(((Deadline) other).deadline));
    }

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
                return Optional.of(DateUtil.parseDate(this.deadline, false));
            } catch (IllegalValueException e) {
                // TODO this should not occur by default, provided that this object was instantiated successfully.
                e.printStackTrace();
                return deadlineAsDate;
            }
        } else {
            return deadlineAsDate;
        }
    }

//    /**
//     * Checks whether a deadline is valid
//     *
//     * @param deadlineToCheck
//     * @return true if deadline exists and is on or after time of checking,
//     *         false if no deadline, multiple deadlines or deadline is before
//     *         time of checking
//     */
//    private static boolean isValidDeadline(String deadlineToCheck) {
//        assert deadlineToCheck != null;
//
//        Date parsed = DateUtil.parseDate(deadlineToCheck);
//
//        if (!DateUtil.hasPassed(parsed)) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }
//
//    /**
//     * Checks whether a deadline is valid
//     *
//     * @param deadlineToCheck
//     * @return true if deadline exists and is on or after time of checking,
//     *         false if no deadline, multiple deadlines or deadline is before
//     *         time of checking
//     */
//    public static boolean isValidDeadline(String deadlineToCheck, boolean isNew) {
//        assert deadlineToCheck != null;
//        if (!isNew) {
//            return true;
//        } else if (deadlineToCheck.equals(NO_DEADLINE)) {
//            return true;
//        }
//        Date rightNow = new Date();
//        PrettyTimeParser dateParser = new PrettyTimeParser();
//        List<Date> parsedDeadline = dateParser.parse(deadlineToCheck);
//
//        // //TODO replace parsing with set deadlines to avoid ambiguity/multiple
//        // deadlines
//        // if (parsedDeadline.size() != 1) {
//        // return false;
//        // }
//
//        Date deadline = parsedDeadline.get(0);
//
//        if (!deadline.before(rightNow)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public boolean hasDeadline() {
        return !deadline.equals(NO_DEADLINE);
    }

}
