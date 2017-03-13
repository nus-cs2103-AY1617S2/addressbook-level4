package org.teamstbf.yats.testutil;

import org.teamstbf.yats.model.item.Deadline;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.Periodic;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Timing;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestEvent implements ReadOnlyEvent {

    private Title name;
    private Periodic period;
    private Timing startTime;
    private Timing endTime;
    private Description description;
    private boolean isDone;
    private Location location;
    private UniqueTagList tags;

    public TestEvent() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestEvent(TestEvent personToCopy) {
        this.name = personToCopy.getTitle();
        this.period = personToCopy.getPeriod();
        this.location = personToCopy.getLocation();
        this.startTime = personToCopy.getStartTime();
        this.endTime = personToCopy.getEndTime();
        this.description = personToCopy.getDescription();
        this.tags = personToCopy.getTags();
    }

    public void setTitle(Title name) {
        this.name = name;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return name;
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
        sb.append("add " + this.getTitle().fullName + " ");
        sb.append("l/" + this.getLocation().value + " ");
        sb.append("p/" + this.getPeriod().value + " ");
        sb.append("s/" + this.getStartTime().value + " ");
        sb.append("e/" + this.getEndTime().value + " ");
        sb.append("d/" + this.getDescription().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Timing getStartTime() {
        return startTime;
    }

    @Override
    public Timing getEndTime() {
        return endTime;
    }

    @Override
    public Periodic getPeriod() {
        return period;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setPeriod(Periodic periodic) {
        this.period = periodic;  
    }
    
    public void setLocation(Location location) {
        this.location = location;  
    }
    
    public void setStartTime(Timing timing) {
        this.startTime = timing;
    }

    public void setEndTime(Timing timing) {
        this.endTime = timing;
    }

    @Override
    public Deadline getDeadline() {
        // TODO Auto-generated method stub
        return null;
    }
}
