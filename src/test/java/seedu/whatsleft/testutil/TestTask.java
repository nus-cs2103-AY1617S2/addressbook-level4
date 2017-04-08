package seedu.whatsleft.testutil;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import seedu.whatsleft.model.activity.ByDate;
import seedu.whatsleft.model.activity.ByTime;
import seedu.whatsleft.model.activity.Description;
import seedu.whatsleft.model.activity.Location;
import seedu.whatsleft.model.activity.Priority;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.model.tag.UniqueTagList;

//@@author A0148038A
/**
 * A mutable Task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private Priority priority;
    private ByTime byTime;
    private ByDate byDate;
    private Location location;
    private UniqueTagList tags;
    private boolean status;

    public TestTask() {
        tags = new UniqueTagList();
        status = false;
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.description = taskToCopy.getDescription();
        this.priority = taskToCopy.getPriority();
        this.byTime = taskToCopy.getByTime();
        this.byDate = taskToCopy.getByDate();
        this.location = taskToCopy.getLocation();
        this.tags = taskToCopy.getTags();
        this.status = taskToCopy.getStatus();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setByTime(ByTime byTime) {
        this.byTime = byTime;
    }

    public void setByDate(ByDate byDate) {
        this.byDate = byDate;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public ByTime getByTime() {
        return byTime;
    }

    @Override
    public ByDate getByDate() {
        return byDate;
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
    public boolean getStatus() {
        return status;
    }

    @Override
    public boolean hasDeadline() {
        return (this.getByDate() != null || this.getByTime() != null);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().description + " ");
        sb.append("p/" + this.getPriority().toString() + " ");
        sb.append("bt/" + this.getByTime().getValue().format(DateTimeFormatter.ofPattern("HHmm")));
        sb.append("bd/" + this.getByDate().getValue().format(DateTimeFormatter.ofPattern("ddMMyy")));
        sb.append("l/" + this.getLocation().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("ta/" + s.tagName + " "));
        return sb.toString();
    }

    //@@author A0124377A
    public String getEditCommand(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("edit ts " + i + " ");
        sb.append("bd/ " + this.getByDate().toString());
        sb.append("bt/ " + this.getByTime().toString());
        return sb.toString();
    }

    //@@author A0148038A
    @Override
    public String getDescriptionToShow() {
        return getDescription().toString();
    }

    @Override
    public String getPriorityToShow() {
        return "Priority: " + getPriority().toString().toUpperCase();
    }

    @Override
    public String getByTimeDateToShow() {
        if (hasDeadline()) {
            return "BY " + this.byTime.toString() + " " + this.byDate.toString();
        } else {
            return null;
        }
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
