package seedu.todolist.model.task;

import java.util.Objects;

import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.model.tag.UniqueTagList;

/**
 * Represents a Task in the todo list. Guarantees: details are present and not
 * null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private StartTime startTime;
    private EndTime endTime;
    private UniqueTagList tags;
    private boolean completed;

    /**
     * name and tags must be present and not null.
     * startTime and endTime are optional fields.
     * @param starTime TODO
     * @param endTime TODO
     */
    public Task(Name name, StartTime startTime, EndTime endTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.completed = false;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(),
                source.getStartTime(),
                source.getEndTime(),
                source.getTags());
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
        return endTime;
    }

    public void setEndTime(EndTime endTime) {
        assert endTime != null;
        this.endTime = endTime;
    }

    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    @Override
    public boolean isComplete() {
        return this.completed;
    }

    public boolean getCompletionStatus() {
        return this.completed;
    }
    
    public void setAsComplete() {
        this.completed = true;
    }
    
    public void setAsIncomplete() {
        this.completed = false;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
