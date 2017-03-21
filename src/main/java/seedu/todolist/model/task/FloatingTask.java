package seedu.todolist.model.task;

import java.util.Objects;

import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.model.tag.UniqueTagList;

public class FloatingTask extends Task {
	
	/**
     * name, tags is guaranteed to be present and not null.
     */
    public FloatingTask(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.completed = false;
    }
    
    /**
     * Overloaded constructor that also takes in isComplete attribute
     * to ensure proper loading from the database.
     */
    public FloatingTask(Name name, UniqueTagList tags, boolean isComplete) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
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
    	return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FloatingTask // instanceof handles nulls
                		&& ((FloatingTask) other).getName().equals(this.getName())
                        && (((FloatingTask) other).isComplete() == this.isComplete()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
    	final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
