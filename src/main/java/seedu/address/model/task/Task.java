package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. not null, field values are validated.
 */
public abstract class Task implements ReadOnlyTask {
    public static final int MAX_TIME_DIFF = 2 ^ 32 - 1;

    private Name name;
    private String id;
    private UniqueTagList tags;

    private boolean done;
    protected boolean today = false;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags, boolean done) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
        this.done = done;
        this.id = "";
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTags(), source.isDone());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        assert replacement != null;
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public abstract void resetData(ReadOnlyTask replacement);

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
        return Objects.hash(name, tags, done);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public abstract TaskType getTaskType();

    @Override
    public abstract String getTaskDateTime();;

    @Override
    public void setToday() {
        today = true;
    }

    @Override
    public boolean isToday() {
        return today;
    }

    @Override
    public abstract DateTime getDeadline();

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public abstract String getTaskAbsoluteDateTime();

    @Override
    public abstract DateTime getStartingTime();

    @Override
    public abstract int compareTo(ReadOnlyTask task2);

}
