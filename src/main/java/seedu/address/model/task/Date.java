// @@author A0163996J

package seedu.address.model.task;

import com.joestelmach.natty.*;

import seedu.address.commons.exceptions.IllegalValueException;

import java.util.List;

/**
 * Represents a Task's start or end date and time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_DATE_FAIL = "Date not valid. Try formats such as: "
            + "'next friday at 2 pm' or '2/12/17'";

    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final java.util.Date date;
    public final String dateString;
    Parser parser = new Parser();

    public Date() {
        this.date = null;
        this.dateString = null;
    }

    /**
     * Validates given date.
     *
     * @throws IllegalValueException
     *             if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        this.dateString = date;
        if (date != null && date.length() > 0) {
            List groups = parser.parse(date);
            if (groups.size() >= 0) {
                try {
                    DateGroup group = (DateGroup) groups.get(0);
                    this.date = group.getDates().get(0);
                } catch (Exception exception) {
                    throw new IllegalValueException(MESSAGE_DATE_FAIL);
                }
            } else {
                this.date = null;
            }
        } else {
            this.date = null;
        }
    }


    @Override
    public String toString() {
        if (date != null) {
            return date.toString();
        } else {
            return "";
        }
    }

    public String originalString() {
        if (dateString != null) {
            return dateString;
        } else {
            return "";
        }
    }
    
    private boolean exists() {
        if (date == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (this.date == null) {
            if (((Date) other).date == null) {
                return true;
            } else {
                return false;
            }
        }
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                        && this.date.equals(((Date) other).date)); // state
                                                                   // check
    }
    
    private boolean isBefore(Date other) {
        if (!this.exists() || !other.exists()) {
            return false;
        }
        return this.date.before(other.date);
    }
    
    public boolean isStartValidComparedToEnd(Date end) {
        if (this.exists() && !end.exists()) {
            return false;
        }
        if (end.isBefore(this)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
