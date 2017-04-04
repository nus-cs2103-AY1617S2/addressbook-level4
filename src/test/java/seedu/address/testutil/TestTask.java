package seedu.address.testutil;

//import java.time.LocalDate;
//import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.person.ByDate;
import seedu.address.model.person.ByTime;
import seedu.address.model.person.Description;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.tag.UniqueTagList;

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
    private boolean hasDeadline;

    public TestTask() {
        tags = new UniqueTagList();
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
        this.hasDeadline = taskToCopy.hasDeadline();
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
        return hasDeadline;
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

    /*
    //@@author
    @Override
    public boolean isOver() {
        if (LocalDate.now().isAfter(this.getByDate().getValue())) {
            return true;
        } else if (LocalDate.now().isBefore(this.getByDate().getValue())) {
            return false;
        } else {
            if (LocalTime.now().isAfter(this.getByTime().getValue())) {
                return true;
            } else {
                return false;
            }
        }
    }
    */
}
