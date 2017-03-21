package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.task.ClockTime;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Time time;
    private ClockTime clockTime;
    private Address address;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Time time, ClockTime clockTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, time, clockTime, tags);
        this.name = name;
        this.time = time;
        this.clockTime = clockTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTime(), source.getClockTime(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setTime(Time time) {
        assert time != null;
        this.time = time;
    }

    @Override
    public Time getTime() {
        return time;
    }
    
    //@@author A0143873Y
    public void setClockTime(ClockTime clockTime) {
        assert clockTime != null;
        this.clockTime = clockTime;
    }

    public ClockTime getClockTime() {
        return clockTime;
    }
    //@@author

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
        this.setTime(replacement.getTime());
        this.setClockTime(replacement.getClockTime());
        //this.setAddress(replacement.getAddress());
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
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, time, clockTime, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
