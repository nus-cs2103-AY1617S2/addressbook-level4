package seedu.address.model.task;

import java.util.Objects;

import seedu.address.model.tag.UniqueTagList;

public class Task {
    
    protected Name name;
    protected Description desc;
    
    protected UniqueTagList tags;
    
    
    public Task(Name name, Description desc, UniqueTagList tags) {
        this.name = name;
        this.desc = desc;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Creates a copy of the given Task.
     */
    public Task(Task source) {
        this(source.getName(), source.getDescription(), source.getTags());
    }
    
    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }
    
    public Name getName() {
        return name;
    }

    public void setDescription(Description desc) {
        assert desc != null;
        this.desc = desc;
    }

    public Description getDescription() {
        return desc;
    }
    
    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
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
    public void resetData(Task replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
    }
    
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.isSameStateAs((Task) other));
    }
    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    public boolean isSameStateAs(Task other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDescription().equals(this.getDescription()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, desc, tags);
    }
    
    public String toString() {
        return getAsText();
    }
    
    /**
     * Formats the person as text, showing all contact details.
     */
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();

    }
}
