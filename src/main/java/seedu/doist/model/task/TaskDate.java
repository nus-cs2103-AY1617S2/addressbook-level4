package seedu.doist.model.task;

import java.util.Date;

import seedu.doist.commons.exceptions.IllegalValueException;

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
            throw new IllegalValueException("Incorrect Dates");
        } else {
            return (startDate.compareTo(endDate) <= 0) ? true : false;
        }
    }
}
