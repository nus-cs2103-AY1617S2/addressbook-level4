package seedu.address.model.todo;

import java.util.Date;
import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Todo in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Todo implements ReadOnlyTodo {

    private Name name;
    private Date starttime;
    private Date endtime;

    private UniqueTagList tags;

    /**
     * Constructor for a floating task
     */
    public Todo(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    public Todo(Todo todo) {
        assert !CollectionUtil.isAnyNull(todo);
        this.name = todo.getName();
        this.tags = new UniqueTagList(todo.getTags()); // protect internal tags from changes in the arg list
    }
    /** for unit-test
     * Constructor for a scheduled task
     */

    public Todo(Name name, Date starttime, Date endtime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, starttime, endtime, tags);
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    public Todo(Name name, Date endtime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, endtime, tags);
        this.name = name;
        this.endtime = endtime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTodo.
     */
    public Todo(ReadOnlyTodo source) {
        this(source.getName(), source.getTags());
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
        if (starttime != null) {
            this.starttime = starttime;
        }
    }

    @Override
    public Date getStartTime() {
        return starttime;
    }

    public void setEndTime(Date endtime) {
        if (endtime != null) {
            this.endtime = endtime;
        }
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
