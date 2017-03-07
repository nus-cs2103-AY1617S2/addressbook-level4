package seedu.geekeep.model.task;

import seedu.geekeep.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the addressbook. Implementations should guarantee: details are
 * present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Title getTitle();

    Phone getPhone();

    DateTime getDateTime();

    Location getLocation();

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on the returned list will not affect the
     * person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                        && other.getTitle().equals(this.getTitle()) // state checks here onwards
                        && other.getPhone().equals(this.getPhone()) && other.getDateTime().equals(this.getDateTime())
                        && other.getLocation().equals(this.getLocation()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle()).append(" Phone: ").append(getPhone()).append(" Email: ").append(getDateTime())
                .append(" Address: ").append(getLocation()).append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
