package seedu.address.model.task;

import java.text.SimpleDateFormat;
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
    public TaskWithDeadline(Name name, UniqueTagList tags, Date deadline, boolean isDeadlineMissingDate,
            boolean isDeadlineMissingTime, Date startingTime, boolean isStartingTimeMissingDate,
            boolean isStartingTimeMissingTime, boolean isDone) throws IllegalValueException {
        super(name, tags, isDone);
        this.deadline = new Deadline(deadline, isDeadlineMissingDate, isDeadlineMissingTime);
        if (startingTime != null) {
            this.startingTime = new StartingTime(startingTime, isStartingTimeMissingDate, isStartingTimeMissingTime);
        }
        validateDateTime();
    }

    /**
     * validates deadline and starting time
     *
     * @throws IllegalValueException
     */
    private void validateDateTime() throws IllegalValueException {
        if (this.startingTime != null && this.startingTime.isAfter(this.deadline)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
    }

    public TaskWithDeadline(ReadOnlyTask source) {
        super(source);
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
        return !this.isDone() && fmt.format(this.deadline.getDate()).equals(fmt.format(new Date()));
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
            return "Begin: " + startingTime.toString() + ";Due: " + deadline.toString();
        }
    }
}
