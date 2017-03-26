package seedu.address.model.task;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. not null, field values are validated.
 */
public abstract class Task implements ReadOnlyTask {

    public static final String ONLY_STARTING_DATE_AVAILABLE_ERROR = "Task should not contain"
            + " a starting time without a deadline";

    private Name name;
    private String id;
    private UniqueTagList tags;

    private boolean done;
    protected boolean manualToday = false;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags, boolean done, boolean manualToday) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
        this.done = done;
        this.manualToday = manualToday;
        this.id = "";
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTags(), source.isDone(), source.isManualToday());
    }

    // @@author A0093999Y
    /**
     * Selecting the Task to construct based on what dates is available
     */
    public static Task createTask(Name name, UniqueTagList tags, Optional<DateTime> deadline,
            Optional<DateTime> startingTime, boolean done, boolean manualToday) throws IllegalValueException {
        if (!deadline.isPresent() && !startingTime.isPresent()) {
            return new FloatingTask(name, tags, done, manualToday);
        } else if (deadline.isPresent() && !startingTime.isPresent()) {
            return new DeadlineTask(name, tags, deadline.get().getDate(), done, manualToday);
        } else if (deadline.isPresent() && startingTime.isPresent()) {
            return new EventTask(name, tags, deadline.get().getDate(), startingTime.get().getDate(), done, manualToday);
        } else {
            throw new IllegalValueException(ONLY_STARTING_DATE_AVAILABLE_ERROR);
        }
    }

    /**
     * Selecting the Task to construct from a ReadOnlyTask
     */
    public static Task createTask(ReadOnlyTask readOnlyTask) throws IllegalValueException {
        return createTask(readOnlyTask.getName(), readOnlyTask.getTags(), readOnlyTask.getDeadline(),
                readOnlyTask.getStartingTime(), readOnlyTask.isDone(), readOnlyTask.isManualToday());
    }

    // @@author
    @Override
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    public void setToday() {
        manualToday = true;
    }

    @Override
    public boolean isManualToday() {
        return manualToday;
    }

    @Override
    public abstract boolean isToday();

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
    public abstract String getTaskDateTime();

    @Override
    public abstract Optional<DateTime> getDeadline();

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
    public abstract Optional<DateTime> getStartingTime();

}
