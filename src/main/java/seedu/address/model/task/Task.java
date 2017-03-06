package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

public abstract class Task {
    
    protected Name name;
    protected Description desc;
    
    protected UniqueTagList tags;
    
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
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    public boolean isSameStateAs(Task other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDescription().equals(this.getDescription()));
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
