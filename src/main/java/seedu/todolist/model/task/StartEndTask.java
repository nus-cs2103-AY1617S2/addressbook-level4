package seedu.todolist.model.task;

import java.util.Objects;

import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.model.tag.UniqueTagList;

//@@author A0141647E
/*
 * Represent a Task with a StartTime and an EndTime.
 */
public class StartEndTask extends Task {

    private static final String TYPE = "StartEndTask";
    private StartTime startTime;
    private EndTime endTime;

    /**
     * name, start time, end time and tags must be present and not null.
     */
    public StartEndTask(Name name, StartTime startTime, EndTime endTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, startTime, endTime, tags);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from
        // changes in the arg list
        this.completed = false;
    }

    /**
     * Overloaded constructor that also takes in isComplete attribute to ensure
     * proper loading from the database.
     */
    public StartEndTask(Name name, StartTime startTime, EndTime endTime, UniqueTagList tags, boolean isComplete) {
        assert !CollectionUtil.isAnyNull(name, startTime, endTime, tags);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from
        // changes in the arg list
        this.completed = isComplete;
    }

    @Override
    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    public void setStartTime(StartTime startTime) {
        assert startTime != null;
        this.startTime = startTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    public void setEndTime(EndTime endTime) {
        assert endTime != null;
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartEndTask // instanceof handles nulls
                        && ((StartEndTask) other).getName().equals(this.getName())
                        && ((StartEndTask) other).getStartTime().equals(this.getStartTime())
                        && ((StartEndTask) other).getEndTime().equals(this.getEndTime())
                        && (((StartEndTask) other).isComplete() == this.isComplete()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, startTime, endTime, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" Start Time: " + getStartTime().toString());
        builder.append(" End Time: " + getEndTime().toString());
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
