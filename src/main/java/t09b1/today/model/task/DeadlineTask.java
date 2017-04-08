package t09b1.today.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import t09b1.today.commons.exceptions.IllegalValueException;
import t09b1.today.model.tag.UniqueTagList;

//@@author A0144422R
/**
 * Represents a Task in the task manager with deadline only. not null, field
 * values are validated.
 */
public class DeadlineTask extends Task {
    static final String MESSAGE_DATETIME_CONSTRAINTS = "Deadline should be after starting time.";

    DateTime deadline;

    public DeadlineTask(Name name, UniqueTagList tags, Date date,
            boolean isDone, boolean manualToday) throws IllegalValueException {
        super(name, tags, isDone, manualToday);
        this.deadline = new DateTime(date);
    }

    // (Obsolete)
    // public DeadlineTask(ReadOnlyTask source) throws IllegalValueException {
    // this(source.getName(), source.getTags(),
    // source.getDeadline().get().getDate(), source.isDone(),
    // source.isManualToday());
    // }

    /**
     * return the overdue status of the task
     *
     * @return true for overdue, false otherwise
     */
    @Override
    public boolean isOverdue() {
        return deadline.getDate().compareTo(new Date()) <= 0;
    }

    /**
     * @return the duration from now to the deadline (may be negative)
     */
    public String timeTilDeadline() {
        return deadline.getDuration(new Date());
    }

    @Override
    public String getTaskDateTime() {
        return "Due: " + deadline.toString();
    }

    @Override
    public boolean isToday() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return manualToday || fmt.format(this.deadline.getDate())
                .equals(fmt.format(new Date())) || isOverdue();
    }

    // @@author A0093999Y
    @Override
    public String getTaskAbsoluteDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss");
        return "Due: " + dateFormat.format(deadline.getDate());
    }
    // @@author A0144422R

    @Override
    public Optional<DateTime> getDeadline() {
        return Optional.of(new DateTime(deadline.getDate()));
    }

    @Override
    public Optional<DateTime> getStartingTime() {
        return Optional.empty();
    }
}
