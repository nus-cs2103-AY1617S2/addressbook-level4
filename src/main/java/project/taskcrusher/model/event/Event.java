package project.taskcrusher.model.event;

import java.util.List;
import java.util.Objects;

import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.UserToDo;
import project.taskcrusher.model.tag.UniqueTagList;

public class Event extends UserToDo implements ReadOnlyEvent {
    private List<Timeslot> timeslots;
    private Location location;
    private boolean isPast;

    public Event(Name name, List<Timeslot> timeslots, Location location,
            Description description, UniqueTagList tags) {
        super(name, null, description, tags); //TODO: remove this stub priority later

        assert !CollectionUtil.isAnyNull(timeslots, location);

        this.timeslots = timeslots;
        this.location = location;
        this.isPast = false;
    }

    /**Checks if any of the Timeslot object in the timeslots list has overlapping start and end date with
     * {@code another}
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
        this(source.getName(), source.getTimeslots(), source.getLocation(),
                source.getDescription(), source.getTags());
    }

    public List<Timeslot> getTimeslots() {
        return this.timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        assert timeslots != null;
        this.timeslots = timeslots;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        assert location != null;
        this.location = location;
    }

    public void setOverdue(boolean toSet) {
        isPast = toSet;
    }

    public boolean isPast() {
        return this.isPast;
    }

    public void resetData(ReadOnlyEvent replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setTimeslots(replacement.getTimeslots());
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
        return Objects.hash(name, timeslots, location, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
