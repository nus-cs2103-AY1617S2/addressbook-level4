//@@author A0139925U
package seedu.tache.model.recurstate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.task.DateTime;

public class RecurState {
    public static final String MESSAGE_RECUR_INTERVAL_CONSTRAINTS = "Unknown recur interval format. "
                                                            + "Valid values include: None, Day, Week, Month and Year";

    public enum RecurInterval { NONE, DAY, WEEK, MONTH, YEAR };

    private RecurInterval interval;
    private List<Date> recurCompletedList;
    private String recurDisplayDate;

    public RecurState() {
        this.interval = RecurInterval.NONE;
        this.recurCompletedList = new ArrayList<Date>();
        this.recurDisplayDate = "";
    }

    public RecurState(RecurInterval interval, List<Date> recurCompletedList) {
        this.interval = interval;
        this.recurCompletedList = recurCompletedList;
        this.recurDisplayDate = "";
    }

    public boolean isRecurring() {
        return !interval.equals(RecurInterval.NONE);
    }

    public RecurInterval getRecurInterval() {
        return interval;
    }

    public void setRecurInterval(RecurInterval interval) throws IllegalValueException {
        if (interval == null) {
            throw new IllegalValueException(MESSAGE_RECUR_INTERVAL_CONSTRAINTS);
        }
        this.interval = interval;
    }

    public boolean isMasterRecurring() {
        return isRecurring() && this.recurDisplayDate.equals("");
    }

    public boolean isGhostRecurring() {
        return isRecurring() && !this.recurDisplayDate.equals("");
    }

    public List<Date> getRecurCompletedList() {
        return this.recurCompletedList;
    }

    public void setRecurCompletedList(List<Date> recurCompletedList) {
        this.recurCompletedList = recurCompletedList;
    }

    public List<Date> getCompletedRecurDates(DateTime startDateTime, DateTime endDateTime, Date filterEndDate) {
        List<Date> completedRecurList = new ArrayList<Date>();
        if (isRecurring()) {
            Calendar calendarCurrent = Calendar.getInstance();
            calendarCurrent.setTime(new Date(startDateTime.getAmericanDateOnly()
                                        + " " + startDateTime.getTimeOnly()));

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(new Date(endDateTime.getAmericanDateOnly()
                                        + " " + endDateTime.getTimeOnly()));
            calendarEnd.add(Calendar.SECOND, 1);

            //Populate 'Ghost' Task
            while (calendarCurrent.getTime().before(calendarEnd.getTime())
                    && (filterEndDate == null || calendarCurrent.getTime().before(filterEndDate))) {

                if (isRecurCompleted(calendarCurrent.getTime())) {
                    completedRecurList.add(calendarCurrent.getTime());
                }

                if (interval.equals(RecurInterval.DAY)) {
                    calendarCurrent.add(Calendar.DATE, 1);
                } else if (interval.equals(RecurInterval.WEEK)) {
                    calendarCurrent.add(Calendar.WEEK_OF_YEAR, 1);
                } else if (interval.equals(RecurInterval.MONTH)) {
                    calendarCurrent.add(Calendar.MONTH, 1);
                } else if (interval.equals(RecurInterval.YEAR)) {
                    calendarCurrent.add(Calendar.YEAR, 1);
                }
            }
        }
        return completedRecurList;

    }

    public List<Date> getUncompletedRecurDates(DateTime startDateTime, DateTime endDateTime, Date filterEndDate) {
        List<Date> uncompletedRecurList = new ArrayList<Date>();
        if (isRecurring()) {
            Calendar calendarCurrent = Calendar.getInstance();
            calendarCurrent.setTime(new Date(startDateTime.getAmericanDateOnly()
                                        + " " + startDateTime.getTimeOnly()));

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(new Date(endDateTime.getAmericanDateOnly()
                                        + " " + endDateTime.getTimeOnly()));
            calendarEnd.add(Calendar.SECOND, 1);

            //Populate 'Ghost' Task
            while (calendarCurrent.getTime().before(calendarEnd.getTime())
                    && (filterEndDate == null || calendarCurrent.getTime().before(filterEndDate))) {

                if (!isRecurCompleted(calendarCurrent.getTime())) {
                    uncompletedRecurList.add(calendarCurrent.getTime());
                }

                if (interval.equals(RecurInterval.DAY)) {
                    calendarCurrent.add(Calendar.DATE, 1);
                } else if (interval.equals(RecurInterval.WEEK)) {
                    calendarCurrent.add(Calendar.WEEK_OF_YEAR, 1);
                } else if (interval.equals(RecurInterval.MONTH)) {
                    calendarCurrent.add(Calendar.MONTH, 1);
                } else if (interval.equals(RecurInterval.YEAR)) {
                    calendarCurrent.add(Calendar.YEAR, 1);
                }
            }
        }
        return uncompletedRecurList;

    }

    public boolean isRecurCompleted(Date recurCompleted) {
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
        for (int i = 0; i < getRecurCompletedList().size(); i++) {
            if (outputFormatter.format(getRecurCompletedList().get(i))
                                .equals(outputFormatter.format(recurCompleted))) {
                return true;
            }
        }
        return false;
    }

    public String getRecurDisplayDate() {
        return this.recurDisplayDate;
    }

    public void setRecurDisplayDate(String recurDisplayDate) {
        this.recurDisplayDate = recurDisplayDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurState // instanceof handles nulls
                && this.interval.equals(((RecurState) other).getRecurInterval())
                && this.recurCompletedList.equals(((RecurState) other).getRecurCompletedList())
                && this.recurDisplayDate.equals(((RecurState) other).getRecurDisplayDate())); // state check
    }
}
