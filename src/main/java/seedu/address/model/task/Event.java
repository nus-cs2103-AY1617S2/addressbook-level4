package seedu.address.model.task;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

//@@ A0122017Y
/**
 * Represents a Event in the endtime book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private Title title;
    private Venue venue;
    private StartTime startTime;
    private EndTime endTime;
    private Description description;
    private UrgencyLevel urgencyLevel;
    private Boolean isEventCompleted;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Event(Title title, Venue venue, StartTime startTime, EndTime endTime, UrgencyLevel urgencyLevel, Description description, boolean status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, status);
        this.title = title;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.urgencyLevel = urgencyLevel;
        this.isEventCompleted = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTitle(), 
                source.getVenue().orElse(null), 
                source.getStartTime(), 
                source.getEndTime(),
                source.getUrgencyLevel().orElse(null), 
                source.getDescription().orElse(null),
                source.hasEventCompleted(),
                source.getTags());
    }

    public void setTitle(Title name) {
        assert name != null;
        this.title = name;
    }

    @Override
    public Title getTitle() {
        return title;
    }
    //@@author A0122017Y  
    @Override
    public boolean hasEventCompleted(){
        return this.isEventCompleted;
    }

    public void setVenue(Venue venue) {
        assert venue != null;
        this.venue = venue;
    }
    
    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public Optional<Venue> getVenue() {
        return Optional.ofNullable(venue);
    }

    @Override
    public StartTime getStartTime() {
        return this.startTime;
    }

    @Override
    public EndTime getEndTime() {
        return this.endTime;
    }
    
    @Override
    public Optional<UrgencyLevel> getUrgencyLevel() {
        return Optional.ofNullable(this.urgencyLevel);
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    @Override
    public Optional<Description> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }
    
    private void setStartTime(StartTime startTime) {
        assert description != null;
        this.startTime = startTime;
    }

    /**
     * Replaces this Event's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        assert description != null;
        tags.setTags(replacement);
    }

    /**
     * Updates this Event with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyEvent replacement) {
        assert replacement != null;

        this.setTitle(replacement.getTitle());
        this.setStartTime(replacement.getStartTime());
        this.setVenue(replacement.getVenue().orElse(null));
        this.setDescription(replacement.getDescription().orElse(null));
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, venue, startTime, endTime, description, tags);
    }


    public int compareTo(Event o){
        if (this.startTime.compareTo(o.startTime) == 0){
            return this.endTime.compareTo(o.endTime);
        }
        else {
            return this.startTime.compareTo(o.startTime);
        }
    }
    
    public static Comparator<? super Event> getComparator() {
        Comparator<Event> byStartTime = (event1, event2) -> event1.compareTo(event2);
        Comparator<Event> byName = (event1, event2) -> event1.getTitle().compareTo(event2.getTitle());
        return byStartTime.thenComparing(byName);
    }
//@@
}
