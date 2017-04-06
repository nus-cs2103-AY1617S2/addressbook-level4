package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
//@@ author A0164032U
public interface ReadOnlyPerson {

    Name getName();

    StartDate getStartDate();

    EndDate getEndDate();

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
                && other.getName().equals(this.getName())
                && ((other.getStartDate() == null && this.getStartDate() == null)
                        || ((other.getStartDate() != null && this.getStartDate() != null)
                                && other.getStartDate().equals(this.getStartDate())))
                && ((other.getEndDate() == null && this.getEndDate() == null)
                        || ((other.getEndDate() != null && this.getEndDate() != null)
                                && other.getEndDate().equals(this.getEndDate()))));
        }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        
        boolean hasStartDate = getStartDate() != null;
        boolean hasEndDate = getEndDate() != null;
        
        builder
        .append(getName())
        .append(hasStartDate ? " Start Date: " : "")
        .append(hasStartDate ? getStartDate() : "")
        .append(hasEndDate ? " End Date: " : "")
        .append(hasEndDate ? getEndDate() : "")
        .append(" Group: ")
        .append(getGroup())
        .append(" Status: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
