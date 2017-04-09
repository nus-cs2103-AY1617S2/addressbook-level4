package seedu.watodo.model.task;

import java.util.Objects;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.tag.UniqueTagList;

//@@author A0143076J-unused
//not used because realized that it was not very useful to split the task into 3 classes based on tasktype.
//initially thought it would give better cohesion but after doing, realized only added a lot of repeated code.
/** Represents an event with a start and end time in the task manager.
 *  Guarantees: details are present and not null, field values are validated.
 */
public class EventTask extends Task implements ReadOnlyTask {

    private DateTime startDateTime;
    private DateTime endDateTime;
    public static final String MESSAGE_EVENT_TASK_CONSTRAINT = "End date/time must be later than start date/time!";
    /**
     * Every field must be present and not null. End time must be later than start time.
     * @throws IllegalValueException
    */
    public EventTask(Description description, DateTime startDate, DateTime endDate, UniqueTagList tags) throws IllegalValueException {
        super(description, tags);
        if (!endDate.isLater(startDate)) {
            throw new IllegalValueException(MESSAGE_EVENT_TASK_CONSTRAINT);
        }
        this.setStartDateTime(startDate);
        this.setEndDateTime(endDate);
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
        this.setStatus(replacement.getStatus());
        //TODO the datetimes in
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other)
                        && this.getStartDateTime().equals(((EventTask) other).getStartDateTime())
                        && this.getEndDateTime().equals(((EventTask) other).getEndDateTime()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, status, startDateTime, endDateTime, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" from: ").append(getStartDateTime().toString())
                .append(" to: ").append(getEndDateTime().toString())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
