package project.taskcrusher.model.event;

import java.util.List;
import java.util.Objects;

import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.tag.UniqueTagList;

public class Event implements ReadOnlyEvent {

    public static final String EVENT_FLAG = "e";

    private Name name;
    private List<Timeslot> timeslots;
    private Location location;
    private Description description;
    private UniqueTagList tags;
    private boolean isPast;

    public Event(Name name, List<Timeslot> timeslots, Location location, Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, timeslots, location, description, tags);

        this.name = name;
        this.timeslots = timeslots;
        this.isPast = false;
        this.location = location;
        this.description = description;
        this.tags = new UniqueTagList(tags);
    }

    /**
     * Checks if any of the Timeslot object in the timeslots list has
     * overlapping start and end date with {@code another}
     *
     * @param another
     * @return true if overlapping, false otherwise
     */
    public boolean hasOverlappingTimeslot(Timeslot another) {
        assert another != null;
        for (Timeslot ts : timeslots) {
            if (ts.isOverlapping(another)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getTimeslots(), source.getLocation(), source.getDescription(), source.getTags());
    }

    public UniqueTagList getTags() {
        return this.tags;
    }

    @Override
    public Name getName() {
        return this.name;
    }

    public Description getDescription() {
        return this.description;
    }

    public List<Timeslot> getTimeslots() {
        return this.timeslots;
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean isPast() {
        // TODO: make this method take in a date object, and compare on the spot
        return this.isPast;
    }

    public void setEventName(Name name) {
        assert name != null;
        this.name = name;
    }

    public void setEventDate(List<Timeslot> timeslots) {
        assert timeslots != null;
        this.timeslots = timeslots;
    }

    public void setLocation(Location location) {
        assert location != null;
        this.location = location;
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    public void setTags(UniqueTagList tags) {
        this.tags.setTags(tags);
        ;
    }

    public boolean isOverdue() {
        return this.isPast;
    }

    public void setOverdue(boolean toSet) {
        isPast = toSet;
    }

    public void resetData(ReadOnlyEvent replacement) {
        assert replacement != null;

        this.setEventName(replacement.getName());
        this.setEventDate(replacement.getTimeslots());
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
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, timeslots, location, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
