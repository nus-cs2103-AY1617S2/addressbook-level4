package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;

public class TaskWithDeadline extends Task {

    static final String MESSAGE_DATETIME_CONSTRAINTS = "Deadline should be after starting time.";

    Deadline deadline = null;
    StartingTime startingTime = null;

    /**
     * starting time may be null
     *
     * the boolean variable indicates whether the default value of deadline or
     * starting time should be used
     */
    public TaskWithDeadline(Name name, UniqueTagList tags, Date date1,
            Date date2, boolean isDone) throws IllegalValueException {
        super(name, tags, isDone);
        this.deadline = new Deadline(date1);
        if (date2 != null) {
            this.startingTime = new StartingTime(date2);
        }
        validateDateTime();
    }

    /**
     * validates deadline and starting time
     *
     * @throws IllegalValueException
     */
    private void validateDateTime() throws IllegalValueException {
        if (this.startingTime != null
                && this.startingTime.getDate().after(this.deadline.getDate())) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
    }

    public TaskWithDeadline(ReadOnlyTask source) throws IllegalValueException {

        this(source.getName(), source.getTags(), source.getDeadline().getDate(),
                source.getStartingTime() != null
                        ? source.getStartingTime().getDate() : null,
                source.isDone());
        today = source.isToday();
    }

    /**
     * return the overdue status of the task
     *
     * @return true for overdue, false otherwise
     */
    public boolean isOverdue() {
        return deadline.getDate().compareTo(new Date()) <= 0;
    }

    /**
     * @return if the task is due today
     */
    public boolean isDueToday() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return !this.isDone() && fmt.format(this.deadline.getDate())
                .equals(fmt.format(new Date()));
    }

    /**
     * @return the duration from now to the deadline (may be negative)
     */
    public String timeTilDeadline() {
        return deadline.getDuration(new Date());
    }

    @Override
    public TaskType getTaskType() {
        if (startingTime == null) {
            return TaskType.TaskWithOnlyDeadline;
        } else {
            return TaskType.TaskWithDeadlineAndStartingTime;
        }
    }

    @Override
    public String getTaskDateTime() {
        if (startingTime == null) {
            return "Due: " + deadline.toString();
        } else {
            return "Begin: " + startingTime.toString() + "; Due: "
                    + deadline.toString();
        }
    }

    @Override
    public boolean isToday() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(deadline.getDate());
        return today || (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2
                        .get(Calendar.DAY_OF_YEAR));
    }

    @Override
    public DateTime getDeadline() {
        return deadline;
    }

    @Override
    public DateTime getStartingTime() {
        return startingTime;
    }

    @Override
    public String getTaskAbsoluteDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss");
        if (startingTime == null) {
            return "Due: " + dateFormat.format(deadline.getDate());
        } else {
            return "Begin: " + dateFormat.format(startingTime.getDate())
                    + "; Due: " + dateFormat.format(deadline.getDate());
        }
    }
}
