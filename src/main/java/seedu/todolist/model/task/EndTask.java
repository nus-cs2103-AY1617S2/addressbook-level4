package seedu.todolist.model.task;

import java.util.Objects;

import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.model.tag.UniqueTagList;

public class EndTask extends Task {
	
    private EndTime endTime;

    /**
     * name, start time, end time and tags must be present and not null.
     */
    public EndTask(Name name, EndTime endTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, endTime, tags);
        this.name = name;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.completed = false;
    }
    
    /**
     * Overloaded constructor that also takes in isComplete attribute
     * to ensure proper loading from the database.
     */
    public EndTask(Name name, EndTime endTime, 
    		UniqueTagList tags, boolean isComplete) {
        assert !CollectionUtil.isAnyNull(name, endTime, tags);
        this.name = name;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
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
    	return null;
    }

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
}
