package seedu.address.model.task;

import java.util.Objects;

import seedu.address.model.tag.UniqueTagList;

public class FloatingTask implements Task {
    
    private Name name;
    private Description desc;
    
    private UniqueTagList tags;

    
    public FloatingTask(Name name, Description desc, UniqueTagList tags) {
        this.name = name;
        this.desc = desc;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given Task.
     */
    public FloatingTask(Task source) {
        this(source.getName(), source.getDescription(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setDescription(Description desc) {
        assert desc != null;
        this.desc = desc;
    }

    @Override
    public Description getDescription() {
        return desc;
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
    public void resetData(Task replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.isSameStateAs((Task) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, desc, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
