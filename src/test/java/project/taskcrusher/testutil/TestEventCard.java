package project.taskcrusher.testutil;

import java.util.Date;
import java.util.List;

import project.taskcrusher.model.event.Location;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.tag.UniqueTagList;

public class TestEventCard implements ReadOnlyEvent {

    private Name name;
    private Description description;
    private Priority priority;
    private Location location;
    private List<Timeslot> timeslots;
    private UniqueTagList tags;
    private boolean isComplete;
    private boolean isOverdue;

    public TestEventCard() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code eventToCopy}.
     */
    public TestEventCard(TestEventCard eventToCopy) {
        this.name = eventToCopy.getName();
        this.priority = eventToCopy.getPriority();
        timeslots.sort(null);
        this.timeslots = eventToCopy.getTimeslots();
        this.description = eventToCopy.getDescription();
        this.tags = eventToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        timeslots.sort(null);
        this.timeslots = timeslots;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add e " + this.getName().name);

        for (int count = 0; count < timeslots.size(); count++) {
            if (count != 0) {
                sb.append(" or " + timeslots.get(count).toString());
            } else {
                sb.append(" d/" + timeslots.get(count).toString());
            }
        }

        if (this.getPriority().hasPriority()) {
            sb.append(" p/" + this.getPriority().priority);
        }

        if (this.getLocation().hasLocation()) {
            sb.append(" l/" + this.getLocation().location);
        }

        if (this.getDescription().hasDescription()) {
            sb.append(" //" + this.getDescription().description + " ");
        }

        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean isComplete() {
        return this.isComplete;
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

    @Override
    public boolean isOverdue() {
        return this.isOverdue;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

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

    @Override
    public Date getEarliestBookedTime() {
        return timeslots.get(0).start;
    }
}
