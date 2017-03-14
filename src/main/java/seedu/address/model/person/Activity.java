package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents an Activity in WhatsLeft.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Activity implements ReadOnlyActivity {

    private Description description;
    private Priority priority;
    private Email email;
    private Location location;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Activity(Description description, Priority priority, Email email, Location location, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, priority, email, location, tags);
        this.description = description;
        this.priority = priority;
        this.email = email;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyActivity.
     */
    public Activity(ReadOnlyActivity source) {
        this(source.getDescription(), source.getPriority(), source.getEmail(), source.getLocation(), source.getTags());
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setEmail(Email email) {
        assert email != null;
        this.email = email;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    public void setLocation(Location location) {
        assert location != null;
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this activity's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this activity with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyActivity replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setPriority(replacement.getPriority());
        this.setEmail(replacement.getEmail());
        this.setLocation(replacement.getLocation());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyActivity // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyActivity) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, priority, email, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
