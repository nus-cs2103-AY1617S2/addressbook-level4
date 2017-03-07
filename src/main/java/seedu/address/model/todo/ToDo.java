package seedu.address.model.todo;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a ToDo in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class ToDo implements ReadOnlyToDo {

    private Name name;
    private Time starttime;
    private Time endtime;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public ToDo(Name name, Time starttime, Time endtime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, starttime, endtime, tags);
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyToDo.
     */
    public ToDo(ReadOnlyToDo source) {
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

    public void setStartTime(Time time) {
        assert time != null;
        this.starttime = starttime;
    }

    @Override
    public Phone getStartTime() {
        return starttime;
    }

    public void setEndTime(Time endtime) {
        assert endtime != null;
        this.endtime = endtime;
    }

    @Override
    public Email getEndTime() {
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
    public void resetData(ReadOnlyToDo replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setStartTime(replacement.getStartTime());
        this.setEndTime(replacement.getEndTime());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyToDo // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyToDo) other));
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
