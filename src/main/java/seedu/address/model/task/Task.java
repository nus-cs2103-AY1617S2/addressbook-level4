package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyPerson {

    private Name name;
    private EndDate end;
    private StartDate start;
    private Email email;
    private Group group;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */


    public Task(Name name, StartDate start, EndDate end, Email email, Group group, UniqueTagList tags) {

        assert !CollectionUtil.isAnyNull(name, start, end, email, group, tags);
        this.name = name;
        this.start = start;
        this.end = end;
        this.email = email;
        this.group = group;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */

    public Task(ReadOnlyPerson source) {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getEmail(), source.getGroup(), source.getTags());

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

    public void setEmail(Email email) {
        assert email != null;
        this.email = email;
    }

    @Override
    public Email getEmail() {
        return email;
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
    public void resetData(ReadOnlyPerson replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setEndDate(replacement.getEndDate());
        this.setStartDate(replacement.getStartDate());
        this.setEmail(replacement.getEmail());
        this.setGroup(replacement.getGroup());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, end, start, email, group, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0163848R
    public static Task factory(Object ...properties) throws IllegalValueException {
        Name name = CollectionUtil.firstOf(properties, Name.class);
        Email email = CollectionUtil.firstOf(properties, Email.class);
        Group group = CollectionUtil.firstOf(properties, Group.class);
        UniqueTagList tags = CollectionUtil.firstOf(properties, UniqueTagList.class);
        StartDate start = CollectionUtil.firstOf(properties, StartDate.class);
        EndDate end = CollectionUtil.firstOf(properties, EndDate.class);
        
        if (CollectionUtil.isAnyNull(name, group, tags)) {
            throw new IllegalValueException("Task Factory: new task requires a name, group, and tag list");
        }
        
        if (start != null && end != null) {
            return new Task(name, start, end, email, group, tags);
        } else if (start == null && end != null) {
            return new DeadlineTask(name, end, email, group, tags);
        } else if (start == null && end == null) {
            return new FloatingTask(name, email, group, tags);
        }
        
        return null;
    }

}
