package seedu.address.model.task;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book. Guarantees: details are present and
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
     * Creates a copy of the given ReadOnlyPerson.
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
    public int compareTo(ReadOnlyTask o){
        return getEndTime().compareTo(o.getEndTime());
    }

    //@@author A0163848R
    public static Task factory(Object ...properties) throws IllegalValueException {
        Name name = CollectionUtil.firstOf(properties, Name.class);
        Group group = CollectionUtil.firstOf(properties, Group.class);
        UniqueTagList tags = CollectionUtil.firstOf(properties, UniqueTagList.class);
        StartDate start = CollectionUtil.firstOf(properties, StartDate.class);
        EndDate end = CollectionUtil.firstOf(properties, EndDate.class);
        
        if (CollectionUtil.isAnyNull(name, group, tags)) {
            throw new IllegalValueException("Task Factory: new task requires a name, group, and tag list");
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
