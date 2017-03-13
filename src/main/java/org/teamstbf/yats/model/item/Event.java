package org.teamstbf.yats.model.item;

import java.util.Objects;

import org.teamstbf.yats.commons.util.CollectionUtil;
import org.teamstbf.yats.model.tag.UniqueTagList;

public class Event implements ReadOnlyEvent {

    private Title name;
    private Periodic period;
    private Timing startTime;
    private Timing endTime;
    private Deadline deadline;
    private Description description;
    private boolean isDone;
    private Location location;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Event(Title name, Location location, Periodic periodic, Timing startTime,
    		Timing endTime, Deadline deadline, Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.period = periodic;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deadline = deadline;
        this.description = description;
        this.isDone = false;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    public Event(ReadOnlyEvent editedReadOnlyPerson) {
        this(editedReadOnlyPerson.getTitle(), editedReadOnlyPerson.getLocation(),
        		editedReadOnlyPerson.getPeriod(), editedReadOnlyPerson.getStartTime(),
                editedReadOnlyPerson.getEndTime(), editedReadOnlyPerson.getDeadline(), editedReadOnlyPerson.getDescription(),
                editedReadOnlyPerson.getTags());
    }

    public Event() {

    }

    public void setTitle(Title name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Title getTitle() {
        return name;
    }

    public void setPeriod(Periodic period) {
        assert period != null;
        this.period = period;
    }

    public Periodic getPeriod() {
        return this.period;
    }

    public void setStartTime(Timing timing) {
        assert startTime != null;
        this.startTime = timing;
    }

    public Timing getStartTime() {
        return startTime;
    }

    public void setEndTime(Timing timing) {
        assert endTime != null;
        this.endTime = timing;
    }

    public Timing getEndTime() {
        return endTime;
    }
    
    public void setDeadline(Deadline deadline) {
    	assert deadline != null;
    	this.deadline = deadline;
    }
    
    public Deadline getDeadline() {
    	return deadline;
    }
    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setLocation(Location location){
        assert location != null;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyEvent replacement) {
        assert replacement != null;

        this.setTitle(replacement.getTitle());
        this.setPeriod(replacement.getPeriod());
        this.setLocation(replacement.getLocation());
        this.setStartTime(replacement.getStartTime());
        this.setEndTime(replacement.getEndTime());
        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyItem // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyItem) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, location, period, startTime, endTime, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
