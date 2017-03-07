package seedu.address.model.todo;

import java.util.Objects;
import java.util.Date;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a ToDo in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Todo implements ReadOnlyTodo {

    private Name name;
    private Date starttime;
    private Date endtime;

    private UniqueTagList tags;

    public Todo(Name name, Date starttime, Date endtime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, starttime, endtime, tags);
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyToDo.
     */
    public Todo(ReadOnlyTodo source) {
        this(source.getName(), source.getStartTime(), source.getEndTime(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setStartTime(Date starttime) {
        assert starttime != null;
        this.starttime = starttime;
    }

    @Override
    public Date getStartTime() {
        return starttime;
    }

    public void setEndTime(Date endtime) {
        assert endtime != null;
        this.endtime = endtime;
    }

    @Override
    public Date getEndTime() {
        return endtime;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this todo's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this todo with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTodo replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setStartTime(replacement.getStartTime());
        this.setEndTime(replacement.getEndTime());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTodo // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTodo) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, starttime, endtime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
