package seedu.address.model.task;

import java.util.Optional;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the to-do list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Title getTitle();
    Optional<StartTime> getStartTime();
    Optional<EndTime> getEndTime();
    Optional<Venue> getVenue();
    Optional<Description> getDescription();
    Optional<UrgencyLevel> getUrgencyLevel();
    
    String getTaskCategory();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the Task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
                && other.getVenue().equals(this.getVenue())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the Task as text, showing all contact details.
     */
    //@@ author:A0122017Y
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Title of task: ")
                .append(getTitle())
                .append(getVenueString())
                .append(getDescriptionString())
                .append(getEndTimeString());

        getTags().forEach(builder::append);
        return builder.toString();
    }
    /**
     * Check if venues are present. 
     * If null, empty string is returned.
     */
    default String getVenueString(){
        return getVenue().isPresent()? "At: " + getVenue().get().toString() : "";
    }
    
    default String getStartTimeString(){
        return getStartTime().isPresent()? "Start at: "+ getStartTime().get().toString() : "";
    }
    
    default String getEndTimeString(){
        return getEndTime().isPresent()? "Done by: "+ getEndTime().get().toString() : "";
    }
    
    default String getUrgencyLevelString(){
        return getUrgencyLevel().isPresent() ? "Urgency level at: " +getUrgencyLevel().get().toString() : "";
    }
    
    default String getDescriptionString(){
        return getDescription().isPresent()? "Description: " + getDescription().get().toString() : "";
    }
    //@@

}
