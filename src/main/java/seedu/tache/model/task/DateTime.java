//@@author A0139925U
package seedu.tache.model.task;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.PrettyTime;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.tache.commons.exceptions.IllegalValueException;


public class DateTime {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Unknown date format. It is recommended to "
                                        + "interchangeably use the following few formats:"
                                        + "\nMM-DD-YY hh:mm:ss or MM/DD/YY 10.30pm";

    private final Date date;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public DateTime(String date) throws IllegalValueException {
        assert date != null;
        String trimmedStartDate = date.trim();
        List<DateGroup> temp = new Parser().parse(trimmedStartDate);
        if (temp.isEmpty()) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.date = temp.get(0).getDates().get(0);
        String syntaxTree = temp.get(0).getSyntaxTree().toStringTree();
        boolean hasExplicitDate = syntaxTree.contains("EXPLICIT_DATE");
        boolean hasExplicitTime = syntaxTree.contains("EXPLICIT_TIME");
        if (hasExplicitDate ^ hasExplicitTime) {
            if (hasExplicitDate) {
                this.date.setHours(0);
                this.date.setMinutes(0);
                this.date.setSeconds(0);
            }
        }
    }

    /**
     * Returns true if a given string is a valid task date.
     */
    public static boolean isValidDate(String test) {
        List<DateGroup> temp = new Parser().parse(test);
        return !temp.isEmpty();
    }

    @Override
    public String toString() {
        return new PrettyTime().format(date);
    }

    public String getDateOnly() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public String getTimeOnly() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public String getAmericanDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(date);
    }

    public String getAmericanDateOnly() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(date);
    }

    //@@author A0142255M
    public String getDateTimeForFullCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(date);
    }

   /* @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.time.equals(((Date) other).time)); // state check
    }*/

    /*@Override
    public int hashCode() {
        return (startDate.hashCode() && endDate.hashCode());
    }*/

}
