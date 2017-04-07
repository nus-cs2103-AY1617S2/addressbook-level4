package project.taskcrusher.model.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
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

    /**
     *  Constructor for event. {@code isOverdue} and {@code isComplete} is set to false.
     *  {@code timeslots} will be sorted from earliest start time to latest start time
     */
    public Event(Name name, List<Timeslot> timeslots, Priority priority, Location location, Description description,
            UniqueTagList tags) {
        super(name, priority, description, tags);

        assert !CollectionUtil.isAnyNull(timeslots, location);
        assert !timeslots.isEmpty();

        timeslots.sort(null);
        this.timeslots = timeslots;
        this.location = location;
        this.isOverdue = false;
    }

    /**
     *  Overloaded constructor for cases we want to specify {@code isOverdue} and
     *  {@code isComplete} fields.
     */
    public Event(Name name, List<Timeslot> timeslots, Priority priority, Location location,
            Description description, UniqueTagList tags, boolean isComplete, boolean isOverdue) {
        super(name, priority, description, tags);

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
        this(source.getName(), source.getTimeslots(), source.getPriority(), source.getLocation(),
                source.getDescription(), source.getTags(), source.isComplete(), source.isOverdue());
    }

    /**
     * Picks the timeslot specified by the {@code timeslotIndex} and removes the rest from {@code timeslots}.
     */
    public boolean confirmTimeslot(int timeslotIndex) {
        Timeslot confirmed = timeslots.get(timeslotIndex);

        //done this way to keep the data state for undo
        timeslots = new ArrayList<Timeslot>();
        timeslots.add(confirmed);
        return true;
    }

    /**
     * Marks the event as overdue if any of the element in {@code timeslots} has passed
     * when checked against {@code currentTime}.
     * Returns true if it is marked as overdue, false otherwise.
     */
    public boolean updateOverdueStatus(Date currentTime) {
        boolean isAnyUpdate = false;
        for (Timeslot timeslot : getTimeslots()) {
            if (currentTime.after(timeslot.end)) {
                markOverdue();
                isAnyUpdate = true;
            }
        }
        return isAnyUpdate;
    }

    public Date getEarliestBookedTime() {
        return timeslots.get(0).start;
    }

    public List<Timeslot> getTimeslots() {
        return this.timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        assert timeslots != null;
        timeslots.sort(null);
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

    public void markComplete() {
        super.markComplete();
        isOverdue = false;
    }

    public void resetData(ReadOnlyEvent replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setTimeslots(replacement.getTimeslots());
        this.setPriority(replacement.getPriority());
        this.setLocation(replacement.getLocation());
        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
        if (replacement.isComplete()) {
            this.markComplete();
        } else {
            this.isOverdue = false;
            updateOverdueStatus(new Date());
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

    /**
     * Checks if any of the {@preexistingEvents} has overlapping start and end date with this event.
     */
    @Override
    public boolean hasOverlappingEvent(List<? extends ReadOnlyEvent> preexistingEvents) {
        for (ReadOnlyEvent event : preexistingEvents) {
            for (Timeslot timeslot : event.getTimeslots()) {
                if (this.hasOverlappingTimeslot(timeslot)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if {@code timeslot} has overlapping start and end date with the timeslots of this event.
     */
    @Override
    public boolean hasOverlappingTimeslot(Timeslot timeslot) {
        assert timeslot != null;
        for (Timeslot ts : timeslots) {
            if (ts.isOverlapping(timeslot)) {
                return true;
            }
        }
        return false;
    }

}
