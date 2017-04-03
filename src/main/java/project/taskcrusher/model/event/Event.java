package project.taskcrusher.model.event;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.UserToDo;
import project.taskcrusher.model.tag.UniqueTagList;

//@@author A0127737X
/**
 * Represents a user event that is bound to one or more specific timeslots
 */
public class Event extends UserToDo implements ReadOnlyEvent {

    public static final String EVENT_FLAG = "e";

    private List<Timeslot> timeslots;
    private Location location;
    private boolean isOverdue;

    public Event(Name name, List<Timeslot> timeslots, Location location, Description description, UniqueTagList tags) {
        super(name, null, description, tags); // TODO: remove this stub priority
                                              // later

        assert !CollectionUtil.isAnyNull(timeslots, location);

        this.timeslots = timeslots;
        this.location = location;
        this.isOverdue = false;
    }

    public Event(Name name, List<Timeslot> timeslots, Location location, Description description,
            UniqueTagList tags, boolean isComplete, boolean isOverdue) {
        super(name, null, description, tags); // TODO: remove this stub priority
        // later

        assert !CollectionUtil.isAnyNull(timeslots, location);

        this.timeslots = timeslots;
        this.location = location;
        this.isComplete = isComplete;
        this.isOverdue = isOverdue;
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getTimeslots(), source.getLocation(), source.getDescription(),
                source.getTags(), source.isComplete(), source.isOverdue());
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

    public boolean confirmTimeslot(int timeslotIndex) {
        Timeslot confirmed = timeslots.get(timeslotIndex);
        timeslots.clear();
        timeslots.add(confirmed);
        return true;
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

    public void markOverdue() {
        isOverdue = true;
    }

    public void unmarkOverdue() {
        isOverdue = false;
    }

    public boolean isOverdue() {
        return this.isOverdue;
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
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, timeslots, location, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public int compareTo(ReadOnlyEvent another) {
        if (this.isComplete) {
            if (another.isComplete()) {
                return 0;
            } else {
                return 1;
            }
        } else if (another.isComplete()) {
            return -1;
        }
        // TODO: just for now
        Date thisEarliest = this.timeslots.get(0).start;
        Date anotherEarliest = another.getTimeslots().get(0).start;

        return thisEarliest.compareTo(anotherEarliest);
    }

    public boolean hasOverlappingEvent(List<? extends ReadOnlyEvent> preexistingEvents) {

        boolean isOverlapping = false;
        for (ReadOnlyEvent roe : preexistingEvents) {
            for (Timeslot roet : roe.getTimeslots()) {
                if (this.hasOverlappingTimeslot(roet)) {
                    isOverlapping = true;
                }
            }
        }
        return isOverlapping;
    }

}
