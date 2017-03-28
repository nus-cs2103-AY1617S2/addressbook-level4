package seedu.todolist.model.task;

import java.util.Objects;

import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.model.tag.UniqueTagList;

//@@author A0141647E
/*
 * Represent a Task with an EndTime but no specified StartTime
 */
public class EndTask extends Task {

    private static final String TYPE = "EndTask";
    private EndTime endTime;


    /**
     * name, start time, end time and tags must be present and not null.
     */
    public EndTask(Name name, EndTime endTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, endTime, tags);
        this.name = name;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from
        // changes in the arg list
        this.completed = false;
    }

    /**
     * Overloaded constructor that also takes in isComplete attribute to ensure
     * proper loading from the database.
     */
    public EndTask(Name name, EndTime endTime, UniqueTagList tags, boolean isComplete) {
        assert !CollectionUtil.isAnyNull(name, endTime, tags);
        this.name = name;
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
        return null;
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
                || (other instanceof EndTask // instanceof handles nulls
                        && ((EndTask) other).getName().equals(this.getName())
                        && ((EndTask) other).getEndTime().equals(this.getEndTime())
                        && (((EndTask) other).isComplete() == this.isComplete()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, endTime, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
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
