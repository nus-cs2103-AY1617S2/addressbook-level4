//@@author A0131125Y
package seedu.toluist.model;

/**
 * Tag model
 */
public class Tag implements Comparable<Tag> {

    private String tagName;

    public Tag() {}

    /**
     * Validates given tag name.
     */
    public Tag(String name) {
        assert name != null;
        String trimmedName = name.trim();
        this.tagName = trimmedName;
    }

    public String getTagName() {
        return tagName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && this.tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    @Override
    public int compareTo(Tag other) {
        return tagName.compareTo(other.tagName);
    }
}
