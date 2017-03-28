package seedu.todolist.model.task;

import seedu.todolist.model.tag.UniqueTagList;

/**
 * Represents a Task in the To-do list.
 */
public abstract class Task {

    protected Name name;
    protected UniqueTagList tags;
    protected boolean completed;

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public boolean isComplete() {
        return this.completed;
    }

    public void setAsComplete() {
        this.completed = true;
    }

    public void setAsIncomplete() {
        this.completed = false;
    }

    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    public abstract StartTime getStartTime();

    public abstract EndTime getEndTime();

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /*
     * Return a Hash Code that is unique to this Task object.
     */
    public abstract int hashCode();

    /**
     * Returns true if both have the same Task type and the same details.
     * Tags are neglected.
     */
    public abstract boolean equals(Object other);

    /**
     * Formats the task as text, showing all details of the task.
     */
    public abstract String toString();
}
