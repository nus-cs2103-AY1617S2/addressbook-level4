// @@author A0163996J

package seedu.taskit.model.task;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.taskit.commons.exceptions.IllegalValueException;
import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_DATE;

/**
 * Represents a Task's start or end date and time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_DATE_FAIL = MESSAGE_INVALID_DATE;

    public static final String DATE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final java.util.Date date;
    public final String dateString;
    Parser parser = new Parser();


    /**
     * Default constructor for Date
     */
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
            List<DateGroup> groups = parser.parse(date);
            try {
                DateGroup group = (DateGroup) groups.get(0);
                this.date = group.getDates().get(0);
            } catch (Exception exception) {
                throw new IllegalValueException(MESSAGE_DATE_FAIL);
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

    public boolean isBefore(Date other) {
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
    
    /**
     * Comparison method for Task Comparable
     */
    public int compareTo(Date o) {
        if (this.date == null || o.date == null) {
            return 0;
        }
        return this.date.compareTo(o.date);
    }

    //@@author A0141872E
    public boolean isEndTimePassCurrentTime() {
        java.util.Date currentDate = new java.util.Date();
        if(date!= null && date.before(currentDate)) {
            return true;
        }
        return false;
    }

    public boolean isDateEqualCurrentDate() {
        java.util.Date currentDate = new java.util.Date();
        if(date!= null && DateUtils.isSameDay(date, currentDate)) {
            return true;
        }
        return false;
    }

    //@@author A0097141H
    public boolean isDateEqualsDate(Date other) {

      return date != null && DateUtils.isSameDay(date, other.date);
    }

    public boolean isMonthEqualsMonth(Date other) {
      Calendar c1 = Calendar.getInstance();
      c1.setTime(date);
      Calendar c2 = Calendar.getInstance();
      c2.setTime(other.date);

      return date != null && c1.get(Calendar.MONTH)==c2.get(Calendar.MONTH);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
