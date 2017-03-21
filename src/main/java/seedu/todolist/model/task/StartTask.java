package seedu.todolist.model.task;

import java.util.Objects;

import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.model.tag.UniqueTagList;

public class StartTask extends Task {

    private StartTime startTime;

    /**
     * name, start time and tags must be present and not null.
     */
    public StartTask(Name name, StartTime startTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, startTime, tags);
        this.name = name;
        this.startTime = startTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from
        // changes in the arg list
        this.completed = false;
    }

    /**
     * Overloaded constructor that also takes in isComplete attribute to ensure
     * proper loading from the database.
     */
    public StartTask(Name name, StartTime startTime, UniqueTagList tags, boolean isComplete) {
        assert !CollectionUtil.isAnyNull(name, startTime, tags);
        this.name = name;
        this.startTime = startTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from
        // changes in the arg list
        this.completed = isComplete;
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public StartTime getStartTime() {
        return startTime;
    }

    public void setStartTime(StartTime startTime) {
        assert startTime != null;
        this.startTime = startTime;
    }

    public EndTime getEndTime() {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTask // instanceof handles nulls
                        && ((StartTask) other).getName().equals(this.getName())
                        && ((StartTask) other).getStartTime().equals(this.getStartTime())
                        && (((StartTask) other).isComplete() == this.isComplete()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, startTime, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" Start Time: " + getStartTime().toString());
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
