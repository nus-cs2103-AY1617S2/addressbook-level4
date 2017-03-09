package seedu.watodo.model.task;

import java.util.Objects;

import seedu.watodo.commons.util.CollectionUtil;
import seedu.watodo.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask implements ReadOnlyFloatingTask {

    private Description description;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public FloatingTask(Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, tags);
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public FloatingTask(ReadOnlyFloatingTask source) {
        this(source.getDescription(), source.getTags());
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
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
    public void resetData(ReadOnlyFloatingTask replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyFloatingTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyFloatingTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
