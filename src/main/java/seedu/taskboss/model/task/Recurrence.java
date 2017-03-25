package seedu.taskboss.model.task;

import java.util.Calendar;
import java.util.Date;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;

public class Recurrence {

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

       Date startDateTime = task.getStartDateTime().getDate();
       Calendar startCalendar = Calendar.getInstance();
       startCalendar.setTime(startDateTime);

       Date endDateTime = task.getEndDateTime().getDate();
       Calendar endCalendar = Calendar.getInstance();
       endCalendar.setTime(endDateTime);

        switch(this.frequency) {
        case DAILY:
            if (startDateTime != null) {
                startCalendar.add(Calendar.DATE, 1);
                startDateTime = startCalendar.getTime();
                task.setStartDateTime(new DateTime(startDateTime.toString()));
            }
            if (endDateTime != null) {
                endCalendar.add(Calendar.DATE, 1);
                endDateTime = endCalendar.getTime();
                task.setEndDateTime(new DateTime(endDateTime.toString()));
            }
            break;

        case WEEKLY:
            if (startDateTime != null) {
                startCalendar.add(Calendar.WEEK_OF_YEAR, 1);
                startDateTime = startCalendar.getTime();
                task.setStartDateTime(new DateTime(startDateTime.toString()));
            }
            if (endDateTime != null) {
                endCalendar.add(Calendar.WEEK_OF_YEAR, 1);
                endDateTime = endCalendar.getTime();
                task.setEndDateTime(new DateTime(endDateTime.toString()));
            }
            break;

        case MONTHLY:
            if (startDateTime != null) {
                startCalendar.add(Calendar.MONTH, 1);
                startDateTime = startCalendar.getTime();
                task.setStartDateTime(new DateTime(startDateTime.toString()));
            }
            if (endDateTime != null) {
                endCalendar.add(Calendar.MONTH, 1);
                endDateTime = endCalendar.getTime();
                task.setEndDateTime(new DateTime(endDateTime.toString()));
            }
            break;

        case YEARLY:
            if (startDateTime != null) {
                startCalendar.add(Calendar.YEAR, 1);
                startDateTime = startCalendar.getTime();
                task.setStartDateTime(new DateTime(startDateTime.toString()));
            }
            if (endDateTime != null) {
                endCalendar.add(Calendar.YEAR, 1);
                endDateTime = endCalendar.getTime();
                task.setEndDateTime(new DateTime(endDateTime.toString()));
            }
            break;
 
        default:
            // NONE
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
