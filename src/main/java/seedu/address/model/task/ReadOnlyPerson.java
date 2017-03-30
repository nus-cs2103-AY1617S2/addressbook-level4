package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyPerson {

    Name getName();

    Date getDate();

    StartDate getStartDate();

    Email getEmail();

    Group getGroup();

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                        && other.getName().equals(this.getName()) // state
                                                                  // checks here
                                                                  // onwards
                        && other.getDate().equals(this.getDate()) && other.getStartDate().equals(this.getStartDate())
                        && other.getEmail().equals(this.getEmail()) && other.getGroup().equals(this.getGroup()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" Start Date: ").append(getStartDate()).append(" End Date: ").append(getDate())
                .append(" Email: ").append(getEmail()).append(" Group: ").append(getGroup()).append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
