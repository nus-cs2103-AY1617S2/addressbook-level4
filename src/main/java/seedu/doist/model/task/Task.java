package seedu.doist.model.task;

import java.util.Objects;

import seedu.doist.commons.util.CollectionUtil;
import seedu.doist.model.tag.UniqueTagList;

/**
 * Represents a Task in the to-do list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Description desc;
    private Priority priority;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description name, Priority priority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.desc = name;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    public Task(Description name, UniqueTagList tags) {
        this(name, new Priority(), tags);
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getPriority(), source.getTags());
    }

    public void setDescription(Description desc) {
        assert desc != null;
        this.desc = desc;
    }

    @Override
    public Description getDescription() {
        return desc;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
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
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setPriority(replacement.getPriority());
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
        return Objects.hash(desc, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
