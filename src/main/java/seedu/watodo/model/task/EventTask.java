package seedu.watodo.model.task;

import java.util.Objects;

import seedu.watodo.model.tag.UniqueTagList;

public class EventTask extends Task implements ReadOnlyTask {
    
    private DateTime startDateTime;
    private DateTime endDateTime;
    
    public EventTask(Description description, DateTime startDate, DateTime dueDate, UniqueTagList tags) {
        super(description, tags);
        this.setStartDateTime(startDate);
        this.setEndDateTime(dueDate);
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
        //TODO the deadline in 
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
