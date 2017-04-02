package seedu.doist.model.task;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_DATES;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.joestelmach.natty.DateGroup;

import seedu.doist.commons.exceptions.IllegalValueException;

//@@author A0147620L
public class TaskDate {

    private Date startDate;
    private Date endDate;

    public TaskDate() {
        this.startDate = null;
        this.endDate = null;
    }

    public TaskDate (Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isPast() {
        if (this.getStartDate() != null && this.getEndDate() != null) {
            Date currentDate = new Date();
            return (this.getEndDate().compareTo(currentDate) < 0);
        } else {
            return false;
        }
    }

    public boolean isFloating() {
        return this.getStartDate() == null || this.getEndDate() == null;
    }

    public boolean isDeadline() {
        // Both not null
        if (!isFloating()) {
            return this.getStartDate().equals(this.getEndDate());
        } else {
            return false;
        }
    }

    public boolean isEvent() {
        return !isDeadline() && !isFloating();
    }

    /**
     * Function to check whether the date input is valid i.e, the Start date is before or equal to the End Date.
     * Also checks if the parsing of dates has been successful
     * @param startDate
     * @param endDate
     * @return
     * @throws IllegalValueException
     */
    public static boolean validateDate (Date startDate, Date endDate) throws IllegalValueException {
        if (startDate == null || endDate == null) {
            throw new IllegalValueException(MESSAGE_INVALID_DATES);
        } else {
            return (startDate.compareTo(endDate) <= 0) ? true : false;
        }
    }

    /**
     * Function to support natural language input for date and time, using a 3rd party library 'Natty'
     * @param date
     * @return extracted Date if parsing is successful, or null if it fails
     */
    public static Date parseDate (String date) {
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        List<DateGroup> groups = parser.parse(date);
        Date extractDate = null;
        boolean flag = false;
        for (DateGroup group:groups) {
            List<Date> dates = group.getDates();
            if (!dates.isEmpty()) {
                extractDate = dates.get(0);
                flag = true;
            }
        }
        return (flag ? extractDate : null);
    }

    @Override
    public String toString() {
        if (this.startDate != null && this.endDate != null) {
            return this.startDate.toString() + "--" + this.endDate.toString();
        } else {
            return "No dates";
        }
    }

    //@@author A0140887W
    // Workaround has been done because equals method does not work for type Date
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TaskDate)) {
            return false;
        }
        TaskDate otherDate = (TaskDate) other;
        if (this.startDate == otherDate.startDate && this.endDate == otherDate.endDate) {
            return true;
        } else if (this.startDate.toString().equals(otherDate.startDate.toString())
                && this.endDate.toString().equals(otherDate.endDate.toString())) {
            return true;
        } else {
            return false;
        }
    }

    //@@author
    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
}
