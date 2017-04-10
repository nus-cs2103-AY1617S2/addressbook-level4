//@@author A0139925U
package seedu.tache.model.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.PrettyTime;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.tache.commons.exceptions.IllegalValueException;


public class DateTime implements Comparable {

    private static final String EXPLICIT_DATE_TREE_IDENTIFIER = "EXPLICIT_DATE";
    private static final String EXPLICIT_TIME_TREE_IDENTIFIER = "EXPLICIT_TIME";

    public static final String MESSAGE_DATE_CONSTRAINTS = "Unknown date format. It is recommended to "
                                        + "interchangeably use the following few formats:"
                                        + "\nMM-DD-YY hh:mm:ss or MM/DD/YY 10.30pm";

    public static final String DEFAULT_TIME_STRING = "00:00:00";

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
        boolean hasExplicitDate = syntaxTree.contains(EXPLICIT_DATE_TREE_IDENTIFIER);
        boolean hasExplicitTime = syntaxTree.contains(EXPLICIT_TIME_TREE_IDENTIFIER);
        if (hasExplicitDate ^ hasExplicitTime) {
            if (hasExplicitDate) {
                this.date.setHours(0);
                this.date.setMinutes(0);
                this.date.setSeconds(0);
            }
        }
    }

    /**
     * Creates a copy of the given DateTime.
     * @throws IllegalValueException if source date string is invalid
     */
    public DateTime(DateTime source) throws IllegalValueException {
        this(source.getAmericanDateTime());
    }

    @Override
    public String toString() {
        return new PrettyTime().format(date);
    }

    public String getDateOnly() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public void setDateOnly(String date) throws IllegalValueException {
        List<DateGroup> temp = new Parser().parse(date);
        if (temp.isEmpty()) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        Date parsedDate = temp.get(0).getDates().get(0);
        this.date.setDate(parsedDate.getDate());
        this.date.setMonth(parsedDate.getMonth());
        this.date.setYear(parsedDate.getYear());
    }

    public void setTimeOnly(String time) throws IllegalValueException {
        List<DateGroup> temp = new Parser().parse(time);
        if (temp.isEmpty()) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        Date parsedTime = temp.get(0).getDates().get(0);
        this.date.setHours(parsedTime.getHours());
        this.date.setMinutes(parsedTime.getMinutes());
        this.date.setSeconds(parsedTime.getSeconds());
    }

    public void setDefaultTime() throws IllegalValueException {
        setTimeOnly(DEFAULT_TIME_STRING);
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

    @Override
    public int compareTo(Object other) {
        assert other instanceof DateTime;
        if (this.equals(other)) {
            return 0;
        } else if (date.after(((DateTime) other).getDate())) {
            return 1;
        } else {
            return -1;
        }
    }

    //@@author A0142255M
    /**
     * Returns a String which represents a moment to be parsed by FullCalendar.
     * A moment (FullCalendar terminology) refers to a point in time.
     */
    public String getDateTimeForFullCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(date);
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.date.equals(((DateTime) other).getDate())); // state check
    }

    /**
     * Returns true if the date is earlier than today.
     */
    public boolean hasPassed() {
        Date today = new Date();
        return this.date.before(today);
    }

    //@@author A0139961U
    /**
     * Checks if the @param date is same as this object's date.
     * @param date: The date to check against this object's date.
     * @return true if the dates are the same, else, return false.
     */
    public boolean isSameDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date).equals(sdf.format(this.date));
    }

    /**
     * Checks if this object's date is today.
     * @return true if the date is today, else, return false.
     */
    public boolean isToday() {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(today).equals(sdf.format(this.date));
    }

    /**
     * Checks if this object's date falls within this week.
     * @return true if the date is within this week, else, return false.
     */
    public boolean isThisWeek() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int thisWeekNo = cal.get(Calendar.WEEK_OF_YEAR);
        cal.setTime(this.date);
        return (thisWeekNo == cal.get(Calendar.WEEK_OF_YEAR));
    }

    /**
     * Removes the time portion in date by setting Hour/Minute/Second/Millisecond to zero.
     * @param date: The object whose time will be remove.
     * @return the date with time removed.
     */
    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //@@author A0150120H
    /**
     * Returns if the parser can find any date and/or time text
     * @param s String to check
     * @return true if it can be parsed as a date and/or time, false otherwise
     */
    public static boolean canParse(String s) {
        return new Parser().parse(s).size() > 0;
    }

    /**
     * Returns if the String contains a time field
     * @param s String to check
     * @return true if the Parser sees a time field, false otherwise
     */
    public static boolean isTime(String s) {
        Parser dateTimeParser = new Parser();
        List<DateGroup> list = dateTimeParser.parse(s);
        if (list.isEmpty()) {
            return false;
        } else {
            return list.get(0).getSyntaxTree().toStringTree().contains(EXPLICIT_DATE_TREE_IDENTIFIER);
        }
    }

    /**
     * Returns if the String contains a date field
     * @param s String to check
     * @return true if the Parser sees a date field, false otherwise
     */
    public static boolean isDate(String s) {
        Parser dateTimeParser = new Parser();
        List<DateGroup> list = dateTimeParser.parse(s);
        if (list.isEmpty()) {
            return false;
        } else {
            return list.get(0).getSyntaxTree().toStringTree().contains(EXPLICIT_DATE_TREE_IDENTIFIER);
        }
    }
    //@@author



    /*@Override
    public int hashCode() {
        return (startDate.hashCode() && endDate.hashCode());
    }*/

}
