package seedu.address.model.task;

import java.util.Optional;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an event in to-do list.
 * Implementations should guarantee: details are present and not null,
 * and filed values are validated
 */
//@@author A0122017Y
public interface ReadOnlyEvent {
    
    Title getTitle();
    Optional <Description> getDescription();
    Optional <Venue> getVenue();
    StartTime getStartTime();
    EndTime getEndTime();
    Optional<UrgencyLevel> getUrgencyLevel();
    boolean hasEventCompleted();
    UniqueTagList getTags();
    
    /**
     * Returns true if both have the same state.
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this 
                || (other != null 
                && other.getTitle().equals(this.getTitle())
                && other.getVenue().equals(this.getVenue())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
                && other.getUrgencyLevel().equals(this.getUrgencyLevel())
                && other.getDescription().equals(this.getDescription()));
    }
    
    /**
     * Formats the event as text, showing all event deatils and status.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Title: ")
               .append(getTitle())
               .append(getDescriptionString())
               .append(getDurationString());
        return builder.toString();
    }
    /**
     * Formats the description as text.
     * If null, empty string is returned
     */
    default String getVenueString(){
        return getVenue().isPresent()? "At: " + getVenue().get().toString() : "";
    }
    
    default String getDescriptionString(){
        return getDescription().isPresent()? "Description: " + getDescription().get().toString() : "";
    }
    
    default String getDurationString(){
        return "From " + getStartTime().toString() + "to " + getEndTime().toString();
    }
    
    default String getStatusString(){
        return hasEventCompleted()? "Status: P" : "Status: Not completed";
    }
    
    default String getUrgencyString(){
        return getUrgencyLevel().isPresent() ? "Urgency Level at: " + getUrgencyLevel().toString() : "";
    }
    
    default String getStatusDisplay(){
        return hasEventCompleted()? getTitle().toString()+" [PAST]!!" : getTitle().toString();
    }
    //@@
}
