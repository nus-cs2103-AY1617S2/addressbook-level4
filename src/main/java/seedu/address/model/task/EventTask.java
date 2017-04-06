package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;

//@@author A0144422R
/**
 * Represents a Task in the task manager with deadline and starting time, also
 * known as an event. not null, field values are validated.
 */
public class EventTask extends Task {
    static final String MESSAGE_DATETIME_CONSTRAINTS = "Deadline should be after starting time.";

    DateTime deadline;
    DateTime startingTime;

    /**
     * starting time may be null
     */
    public EventTask(Name name, UniqueTagList tags, Date date1, Date date2,
            boolean isDone, boolean manualToday) throws IllegalValueException {
        super(name, tags, isDone, manualToday);
        this.deadline = new DateTime(date1);
        this.startingTime = new DateTime(date2);
        validateDateTime();
    }

    // (Obsolete)
    // public EventTask(ReadOnlyTask source) throws IllegalValueException {
    // this(source.getName(), source.getTags(),
    // source.getDeadline().get().getDate(),
    // source.getStartingTime().get().getDate(), source.isDone(),
    // source.isManualToday());
    // validateDateTime();
    // }

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
        return "Begin: " + startingTime.toString() + "; Due: "
                + deadline.toString();
    }

    // @@author A0093999Y
    @Override
    public boolean isToday() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date timeNow = new Date();
        boolean startToday = fmt.format(this.startingTime.getDate())
                .equals(fmt.format(timeNow));
        boolean withinStartAndEnd = startingTime.getDate()
                .compareTo(timeNow) <= 0
                && deadline.getDate().compareTo(timeNow) >= 0;
        return manualToday || startToday || withinStartAndEnd || isOverdue();
    }
    // @@author A0144422R

    @Override
    public Optional<DateTime> getDeadline() {
        return Optional.of(new DateTime(deadline.getDate()));
    }

    @Override
    public Optional<DateTime> getStartingTime() {
        return Optional.of(new DateTime(startingTime.getDate()));
    }

    @Override
    public String getTaskAbsoluteDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss");
        return "Begin: " + dateFormat.format(startingTime.getDate())
                + "\n   Due: " + dateFormat.format(deadline.getDate());
    }

}
