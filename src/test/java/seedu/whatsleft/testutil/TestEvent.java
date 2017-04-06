package seedu.whatsleft.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import seedu.whatsleft.model.activity.Description;
import seedu.whatsleft.model.activity.EndDate;
import seedu.whatsleft.model.activity.EndTime;
import seedu.whatsleft.model.activity.Location;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.StartDate;
import seedu.whatsleft.model.activity.StartTime;
import seedu.whatsleft.model.tag.UniqueTagList;

/**
 * A mutable Event object. For testing only.
 */
public class TestEvent implements ReadOnlyEvent {

    private Description description;
    private StartTime startTime;
    private StartDate startDate;
    private EndTime endTime;
    private EndDate endDate;
    private Location location;
    private UniqueTagList tags;

    public TestEvent() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code eventToCopy}.
     */
    public TestEvent(TestEvent eventToCopy) {
        this.description = eventToCopy.getDescription();
        this.startTime = eventToCopy.getStartTime();
        this.startDate = eventToCopy.getStartDate();
        this.endTime = eventToCopy.getEndTime();
        this.endDate = eventToCopy.getEndDate();
        this.location = eventToCopy.getLocation();
        this.tags = eventToCopy.getTags();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime = startTime;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public void setEndTime(EndTime endTime) {
        this.endTime = endTime;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
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
        return startTime;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    @Override
    public EndDate getEndDate() {
        return endDate;
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

    //@@author A0110491U
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().description);
        sb.append(" st/" + this.getStartTime().getValue().format(DateTimeFormatter.ofPattern("HHmm")));
        sb.append(" sd/" + this.getStartDate().getValue().format(DateTimeFormatter.ofPattern("ddMMyy")));
        sb.append(" et/" + this.getEndTime().getValue().format(DateTimeFormatter.ofPattern("HHmm")));
        sb.append(" ed/" + this.getEndDate().getValue().format(DateTimeFormatter.ofPattern("ddMMyy")));
        sb.append(" l/" + this.getLocation().value);
        this.getTags().asObservableList().stream().forEach(s -> sb.append(" ta/" + s.tagName + " "));
        return sb.toString();
    }

    //@@author
    @Override
    public boolean isOver() {
        if (LocalDate.now().isAfter(this.getEndDate().getValue())) {
            return true;
        } else if (LocalDate.now().isBefore(this.getEndDate().getValue())) {
            return false;
        } else {
            if (LocalTime.now().isAfter(this.getEndTime().getValue())) {
                return true;
            } else {
                return false;
            }
        }
    }

    //@@author A0148038A
    @Override
    public String getDescriptionToShow() {
        return getDescription().toString();
    }

    @Override
    public String getDurationToShow() {
        return getStartTime().toString() + " " + getStartDate().toString()
               +  " ~ " + getEndTime().toString() + " " + getEndDate().toString();
    }

    @Override
    public String getLocationToShow() {
        if (getLocation().toString() != null) {
            return "@" + getLocation().toString();
        } else {
            return null;
        }
    }

    @Override
    public List<String> getTagsToShow() {
        return tags
                .asObservableList()
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }
}
