package seedu.watodo.model.task;

import java.util.Objects;

import seedu.watodo.model.tag.UniqueTagList;

//@@author A0143076J-unused
//not used because realized that it was not very useful to split the task into 3 classes based on tasktype.
//initially thought it would give better cohesion but after doing, realized only added a lot of repeated code.
/** Represents a task that has to be done by a specific deadline in the task manager.
 * * Guarantees: details are present and not null, field values are validated.
 */
public class DeadlineTask extends Task implements ReadOnlyTask {

    private DateTime deadline;

    public DeadlineTask(Description description, DateTime dateTime, UniqueTagList tags) {
        super(description, tags);
        this.deadline = dateTime;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
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
                        && this.getDeadline().equals(((DeadlineTask) other).getDeadline()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, status, deadline, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" by: ").append(getDeadline().toString())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
