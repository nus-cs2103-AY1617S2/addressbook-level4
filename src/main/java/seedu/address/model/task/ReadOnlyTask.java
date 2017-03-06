package seedu.address.model.task;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Date getDate();
    Time getTime();
    Tag getTag();
    Description getDescription();
    Venue getVenue();
    Priority getPriority();
    boolean isFavorite();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getTime().equals(this.getTime())
                && other.getTag().equals(this.getTag())
                && other.getDescription().equals(this.getDescription())
                && other.getVenue().equals(this.getVenue())
                && other.getPriority().equals(this.getPriority())
                && other.isFavorite()==this.isFavorite());
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (getDate()!=null){
            builder.append("  Due Date:");
            builder.append(getDate());
        }
        return builder.toString();
    }

}
