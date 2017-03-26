package seedu.address.model.person;

import java.util.Comparator;
import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

//@@author A0148038A
/**
 * Represents an event in WhatsLeft
 */
public class Event implements ReadOnlyEvent{

    private Description description;
    private StartTime startTime;
    private StartDate startDate;
    private EndTime endTime;
    private EndDate endDate;
    private Location location;

    private UniqueTagList tags;

    /**
     * Description and start date must be present and not null.
     */
    public Event(Description description, StartTime startTime, StartDate startDate,
    		EndTime endTime, EndDate endDate, Location location, UniqueTagList tags) {
    	
    	//check description and start date are present
        assert !CollectionUtil.isAnyNull(description, startDate);
        
        this.description = description;
        this.startTime = startTime;
        this.startDate = startDate;
        this.endTime = endTime;
        this.endDate = endDate;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getDescription(), source.getStartTime(), source.getStartDate(),
        		source.getEndTime(), source.getEndDate(), source.getLocation(), source.getTags());
    }

    public void setDescription(Description description) {
        assert description != null; // description must be present
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }
    
    public void setStartTime(StartTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    public void setStartDate(StartDate startDate) {
        assert startDate != null; // start date must be present
        this.startDate = startDate;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }
    
    public void setEndTime(EndTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public EndDate getEndDate() {
        return endDate;
    }

    public void setLocation(Location location) {
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
     * Replaces this event's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this event with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyEvent replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setStartTime(replacement.getStartTime());
        this.setStartDate(replacement.getStartDate());
        this.setEndTime(replacement.getEndTime());
        this.setEndDate(replacement.getEndDate());
        this.setLocation(replacement.getLocation());
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
        // use this method for custend fields hashing instead of implementing your own
        return Objects.hash(description, startTime, startDate, endTime, endDate, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
