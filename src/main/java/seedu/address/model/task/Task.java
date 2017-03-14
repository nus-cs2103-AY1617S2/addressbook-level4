package seedu.address.model.task;

import java.util.Objects;

import seedu.address.model.tag.UniqueTagList;

public class Task implements ReadOnlyTask{
    
    protected Title title;
    
    protected UniqueTagList tags;
    
    
    public Task(Title title, UniqueTagList tags) {
        this.title = title;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Creates a copy of the given Task.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getTags());
    }
    
    public void setTitle(Title title) {
        assert title != null;
        this.title = title;
    }
    
    public Title getTitle() {
        return title;
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
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setTitle(replacement.getTitle());
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
                && other.getTitle().equals(this.getTitle()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, tags);
    }
    
    public String toString() {
        return getAsText();
    }
    
    /**
     * Formats the person as text, showing all contact details.
     */
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();

    }
}
