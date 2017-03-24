package org.teamstbf.yats.model.item;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {

    public static final String MESSAGE_TIME_ERROR = "Invalid or empty date/time entry";
    public static final String STRING_EMPTY = "";
    public static final String MESSAGE_TIME_CONSTRAINTS = "by today tomorrow till";
    private Date scheduleDate;

    /*
     * Creates a Schedule object from the Date object given. Date can be null.
     */
    public Schedule(Date dateObject) {
        this.scheduleDate = dateObject;
    }
    
    public Schedule(String timeString) {
        //CHANGE THIS
        this.scheduleDate = null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                        && this.toString().equals(((Schedule) other).toString())); // state
                                                                                    // check
    }

    /*
    public SimpleDate getDate() {
        return this.date;
    }
    public Timing getTime() {
        return this.time;
    }
    public String getValue() {
        return this.value;
    }
    */

    @Override
    public int hashCode() {
        return this.scheduleDate.hashCode();
    }

    /*
    public void setDate(SimpleDate endTime) {
        this.date = endTime;
    }
    public void setTime(Timing startTime) {
        this.time = startTime;
    }
    */

    @Override
    public String toString() {
        if (this.scheduleDate == null) {
            return STRING_EMPTY;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mma dd/MM/yyyy");
        String dateString = dateFormat.format(this.scheduleDate);
        return dateString;
    }

}
