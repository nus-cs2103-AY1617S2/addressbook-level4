package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private EndDate end;
    private StartDate start;
    private Group group;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */


    public Task(Name name, StartDate start, EndDate end, Group group, UniqueTagList tags) {

        assert !CollectionUtil.isAnyNull(name, start, end, group, tags);
        this.name = name;
        this.start = start;
        this.end = end;
        this.group = group;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */

    public Task(ReadOnlyTask source) {
        this(
            source.getName(),
            source.getStartDate(),
            source.getEndDate(),
            source.getGroup(),
            source.getTags());

    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setEndDate(EndDate date) {
        assert date != null;
        this.end = date;
    }

    @Override
    public EndDate getEndDate() {
        return end;
    }

    public void setStartDate(StartDate sdate) {
        assert sdate != null;
        this.start = sdate;
    }

    @Override
    public StartDate getStartDate() {
        return start;
    }

    public void setGroup(Group group) {
        assert group != null;
        this.group = group;
    }

    @Override
    public Group getGroup() {
        return group;
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

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setEndDate(replacement.getEndDate());
        this.setStartDate(replacement.getStartDate());
        this.setGroup(replacement.getGroup());
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
        return Objects.hash(name, end, start, group, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0164032U
    @Override
    public java.util.Date getStartTime() {
        if (start != null) {
            return start.getTime();
        }
        return new java.util.Date(Long.MIN_VALUE);
    }

    @Override
    public java.util.Date getEndTime() {
        if (end != null) {
            return end.getTime();
        }
        return new java.util.Date(Long.MAX_VALUE);
    }

    //@@author A0164032U
    public int compareTo(ReadOnlyTask o) {
        return getEndTime().compareTo(o.getEndTime());
    }

    private static final String FACTORY_ERROR_NULL =
            "Task Factory: new task requires a name, group, and tag list.";
    private static final String FACTORY_ERROR_TIME =
            "Task Factory: new task's end time is before the start time.";
    private static final String FACTORY_ERROR_NOEND =
            "Task Factory: new task requires an end date if given a start date.";

    //@@author A0163848R
    /**
     * Factory method to build a Task or Task-inheriting class from a given unordered array of properties.
     * @param Properties to build Task with
     * @return New Task with given properties
     * @throws IllegalValueException Insufficient/invalid properties given
     */
    public static Task factory(Object ...properties) throws IllegalValueException {
        Name name = CollectionUtil.firstOf(properties, Name.class);
        Group group = CollectionUtil.firstOf(properties, Group.class);
        UniqueTagList tags = CollectionUtil.firstOf(properties, UniqueTagList.class);
        StartDate start = CollectionUtil.firstOf(properties, StartDate.class);
        EndDate end = CollectionUtil.firstOf(properties, EndDate.class);

        if (CollectionUtil.isAnyNull(name, group, tags)) {
            throw new IllegalValueException(FACTORY_ERROR_NULL);
        }

        if (start != null && end != null) {
            return new Task(name, start, end, group, tags);
        } else if (start == null && end != null) {
            return new DeadlineTask(name, end, group, tags);
        } else if (start == null && end == null) {
            return new FloatingTask(name, group, tags);
        }

        return null;
    }


    @Override
    public boolean hasPassed() {
        return getEndTime().before(new java.util.Date());
    }

}
