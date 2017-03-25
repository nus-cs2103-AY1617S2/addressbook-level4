package seedu.taskboss.model.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;

//@@author A0143157J
public class Recurrence {

    public static final String MESSAGE_RECURRENCE_CONSTRAINTS =
            "Task recurrence can be daily/weekly/monthly/yearly,"
            + " and is none by default.";

    public enum Frequency {
        DAILY, WEEKLY, MONTHLY, YEARLY, NONE
    }

    private Frequency frequency;

    public Recurrence(Frequency frequency) {
        assert frequency != null;
        this.frequency = frequency;
    }

    /**
     * Marks a recurring task undone and
     * updates task dates according to the recurrence of the task
     * @throws IllegalValueException
     */
    public void updateTaskDates(Task task) throws IllegalValueException {

        // mark task as undone (i.e remove it from "Done" category)
        UniqueCategoryList newCategoryList = new UniqueCategoryList();
        for (Category category : task.getCategories()) {
            if (!category.equals("Done")) {
                newCategoryList.add(category);
            }
        }
        task.setCategories(newCategoryList);

       Date startDate = task.getStartDateTime().getDate();
       Date endDate = task.getEndDateTime().getDate();
       SimpleDateFormat startSdfFormat = initSimpleDateFormat(task.getStartDateTime());
       SimpleDateFormat endSdfFormat = initSimpleDateFormat(task.getEndDateTime());

        switch(this.frequency) {
        case DAILY:
            if (startDate != null) {
                Calendar startCalendar = initCalendar(task, startDate);
                startCalendar.add(Calendar.DATE, 1);
                String startDateStr = startSdfFormat.format(startCalendar.getTime());
                task.setStartDateTime(new DateTime(startDateStr));
            }
            if (endDate != null) {
                Calendar endCalendar = initCalendar(task, endDate);
                endCalendar.add(Calendar.DATE, 1);
                String endDateStr = endSdfFormat.format(endCalendar.getTime());
                task.setEndDateTime(new DateTime(endDateStr));
            }
            break;

        case WEEKLY:
            if (startDate != null) {
                Calendar startCalendar = initCalendar(task, startDate);
                startCalendar.add(Calendar.WEEK_OF_YEAR, 1);
                String startDateStr = startSdfFormat.format(startCalendar.getTime());
                task.setStartDateTime(new DateTime(startDateStr));
            }
            if (endDate != null) {
                Calendar endCalendar = initCalendar(task, endDate);
                endCalendar.add(Calendar.WEEK_OF_YEAR, 1);
                String endDateStr = endSdfFormat.format(endCalendar.getTime());
                task.setEndDateTime(new DateTime(endDateStr));
            }
            break;

        case MONTHLY:
            if (startDate != null) {
                Calendar startCalendar = initCalendar(task, startDate);
                startCalendar.add(Calendar.MONTH, 1);
                String startDateStr = startSdfFormat.format(startCalendar.getTime());
                task.setStartDateTime(new DateTime(startDateStr));
            }
            if (endDate != null) {
                Calendar endCalendar = initCalendar(task, endDate);
                endCalendar.add(Calendar.MONTH, 1);
                String endDateStr = endSdfFormat.format(endCalendar.getTime());
                task.setEndDateTime(new DateTime(endDateStr));
            }
            break;

        case YEARLY:
            if (startDate != null) {
                Calendar startCalendar = initCalendar(task, startDate);
                startCalendar.add(Calendar.YEAR, 1);
                String startDateStr = startSdfFormat.format(startCalendar.getTime());
                task.setStartDateTime(new DateTime(startDateStr));
            }
            if (endDate != null) {
                Calendar endCalendar = initCalendar(task, endDate);
                endCalendar.add(Calendar.YEAR, 1);
                String endDateStr = endSdfFormat.format(endCalendar.getTime());
                task.setEndDateTime(new DateTime(endDateStr));
            }
            break;

        case NONE:
            break; // do nothing

        default:
            throw new IllegalValueException(Recurrence.MESSAGE_RECURRENCE_CONSTRAINTS);
        }
    }

    private Calendar initCalendar(Task task, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private SimpleDateFormat initSimpleDateFormat(DateTime dateTime) {
        if (dateTime.isTimeInferred()) {
            return new SimpleDateFormat("MMM dd, yyyy");
        } else {
            return new SimpleDateFormat("MMM dd, yyyy h:mm aa");
        }
    }

    public boolean isRecurring() {
        return this.frequency != Frequency.NONE;
    }

    public Frequency getFrequency() {
        return this.frequency;
    }

    public void setFrequency(Frequency freq) {
        this.frequency = freq;
    }

    @Override
    public String toString() {
        switch(this.frequency) {
        case DAILY :
            return "DAILY";
        case WEEKLY:
            return "WEEKLY";
        case MONTHLY:
            return "MONTHLY";
        case YEARLY:
            return "YEARLY";
        default:
            return "NONE";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurrence // instanceof handles nulls
                && this.frequency.equals(((Recurrence) other).frequency)); // state check
    }
}
