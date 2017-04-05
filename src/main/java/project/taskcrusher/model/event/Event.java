package project.taskcrusher.model.event;

import java.util.ArrayList;
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
        //this approach, as opposed to timeslots.clear(), is taken so that we can maintain the reference
        //to timeslot elements in the undo/redo saved states
        timeslots = new ArrayList<Timeslot>();
        timeslots.add(confirmed);
        return true;
    }

    public void updateOverdueStatus() {
        Date now = new Date();
        for (Timeslot timeslot: getTimeslots()) {
            if (now.after(timeslot.end)) {
                markOverdue();
            }
        }
    }

    public Date getEarliestBookedTime() {
        Date earliest = timeslots.get(0).start;
        for (int i = 1; i < timeslots.size(); i++) {
            if (timeslots.get(i).start.before(earliest)) {
                earliest = timeslots.get(i).start;
            }
        }
        return earliest;
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
        if (replacement.isComplete()) {
            this.markComplete();
        }
        if (replacement.isOverdue()) {
            this.markOverdue();
        }
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

        return this.getEarliestBookedTime().compareTo(another.getEarliestBookedTime());
    }

    public boolean hasOverlappingEvent(List<? extends ReadOnlyEvent> preexistingEvents) {
        for (ReadOnlyEvent roe : preexistingEvents) {
            for (Timeslot roet : roe.getTimeslots()) {
                if (this.hasOverlappingTimeslot(roet)) {
                    return true;
                }
            }
        }
        return false;
    }

}
