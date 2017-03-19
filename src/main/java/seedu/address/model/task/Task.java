package seedu.address.model.task;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the to-do list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask{

    private Title title;
    private Venue venue;
    private EndTime endTime;
    private Description description;
    private Boolean isTaskCompleted;
    private UrgencyLevel urgencyLevel;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, Venue venue, EndTime endTime, UrgencyLevel urgencyLevel, Description description, boolean status,  UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, status);
        this.title = title;
        this.venue = venue;
        this.endTime = endTime;
        this.description = description;
        this.isTaskCompleted = status;
        this.urgencyLevel = urgencyLevel;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), 
                source.getVenue().orElse(null), 
                source.getEndTime().orElse(null),
                source.getUrgencyLevel().orElse(null),
                source.getDescription().orElse(null), 
                source.hasTaskCompleted(),
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
    public void setEndTime(EndTime endTime){
        this.endTime = endTime;
    }
    
    public EndTime getEndTime(EndTime endTime){
        return this.endTime;
    }
    
    @Override
    public Boolean hasTaskCompleted(){
        return this.isTaskCompleted;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public Optional<Venue> getVenue() {
        return Optional.ofNullable(this.venue);
    }
    
    @Override
    public Optional<UrgencyLevel> getUrgencyLevel() {
        return Optional.ofNullable(this.urgencyLevel);
    }
    
    @Override
    public Optional<EndTime> getEndTime() {
        return Optional.ofNullable(this.endTime);
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    @Override
    public Optional<Description> getDescription() {
        return Optional.ofNullable(this.description);
    }

    public void setDescription(Description description) {
        this.description = description;
    }


    /**
     * Replaces this Task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this Task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;
        this.setTitle(replacement.getTitle());
        this.setEndTime(replacement.getEndTime().orElse(null));
        this.setVenue(replacement.getVenue().orElse(null));
        this.setDescription(replacement.getDescription().orElse(null));
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, venue, endTime, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
