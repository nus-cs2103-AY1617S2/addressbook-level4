package project.taskcrusher.model.event;

import java.util.Objects;
import java.util.Optional;

import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.tag.UniqueTagList;

public class Event implements ReadOnlyEvent {
    private Name eventName;
    private EventDate eventDate;
    private Optional<Location> location;
    private Optional<Description> description;
    private UniqueTagList tags;

    public Event(Name eventName, EventDate eventDate, Optional<Location> location,
            Optional<Description> description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(eventName, eventDate, location, description, tags);

        this.eventName = eventName;
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
        this.tags = new UniqueTagList(tags);
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getEventName(), source.getEventDate(), source.getLocation(),
                source.getDescription(), source.getTags());
    }

    public UniqueTagList getTags() {
        return this.tags;
    }

    public Name getEventName() {
        return this.eventName;
    }

    public Optional<Description> getDescription() {
        return this.description;
    }

    public EventDate getEventDate() {
        return this.eventDate;
    }

    public Optional<Location> getLocation() {
        return this.location;
    }

    public void setEventName(Name eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(EventDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setLocation(Optional<Location> location) {
        this.location = location;
    }

    public void setDescription(Optional<Description> description) {
        this.description = description;
    }

    public void setTags(UniqueTagList tags) {
        this.tags.setTags(tags);;
    }


    public void resetData(ReadOnlyEvent replacement) {
        this.setEventName(replacement.getEventName());
        this.setEventDate(replacement.getEventDate());
        this.setLocation(replacement.getLocation());
        this.setDescription(replacement.getDescription());
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
        return Objects.hash(eventName, eventDate, location, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
