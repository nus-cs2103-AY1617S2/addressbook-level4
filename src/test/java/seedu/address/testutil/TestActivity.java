package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.person.Description;
import seedu.address.model.person.EndDate;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.Location;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.StartTime;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable Event object. For testing only.
 */
public class TestActivity implements ReadOnlyEvent {

    private Description description;
    private Location location;
    private StartTime starttime;
    private StartDate startdate;
    private EndTime endtime;
    private EndDate enddate;
    private UniqueTagList tags;

    public TestActivity() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code activityToCopy}.
     */
    public TestActivity(TestActivity eventToCopy) {
        this.description = eventToCopy.getDescription();
        this.starttime = eventToCopy.getStartTime();
        this.startdate = eventToCopy.getStartDate();
        this.endtime = eventToCopy.getEndTime();
        this.enddate = eventToCopy.getEndDate();
        this.location = eventToCopy.getLocation();
        this.tags = eventToCopy.getTags();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStartTime(StartTime starttime) {
        this.starttime = starttime;
    }
    
    public void setStartDate(StartDate startdate) {
        this.startdate = startdate;
    }
    
    public void setEndTime(EndTime endtime) {
        this.endtime = endtime;
    }
    
    public void setEndDate(EndDate enddate) {
        this.enddate = enddate;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public StartTime getStartTime() {
        return starttime;
    }
    
    @Override
    public StartDate getStartDate() {
        return startdate;
    }
    
    @Override
    public EndTime getEndTime() {
        return endtime;
    }
    
    @Override
    public EndDate getEndDate() {
        return enddate;
    }
   

    @Override
    public Location getLocation() {
        return location;
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
        sb.append("add " + this.getDescription().description + " ");
        sb.append("sd/" + this.getStartDate().value + " ");
        sb.append("st/" + this.getStartTime().value + " ");
        sb.append("ed/" + this.getEndDate().value + " ");
        sb.append("et/" + this.getEndTime().value + " ");
        sb.append("l/" + this.getLocation().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("ta/" + s.tagName + " "));
        return sb.toString();
    }
    
    @Override
    public boolean isOver() {
        if (LocalDate.now().isAfter(this.getEndDate().getValue())) {
            return true;
        }
        else if (LocalDate.now().isBefore(this.getEndDate().getValue())) {
            return false;
        }
        else {
            if (LocalTime.now().isAfter(this.getEndTime().getValue())) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}
