package seedu.doit.testutil;

import static org.junit.Assert.assertTrue;

import seedu.doit.model.item.Description;
import seedu.doit.model.item.EndTime;
import seedu.doit.model.item.Name;
import seedu.doit.model.item.Priority;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.StartTime;
import seedu.doit.model.tag.UniqueTagList;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask, Comparable<ReadOnlyTask> {

    private Name name;
    private Description description;
    private StartTime startTime;
    private EndTime endTime;
    private Priority priority;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.priority = taskToCopy.getPriority();
        this.startTime = taskToCopy.getStartTime();
        this.endTime = taskToCopy.getEndTime();
        this.description = taskToCopy.getDescription();
        this.tags = taskToCopy.getTags();
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime =  startTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    public void setDeadline(EndTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public boolean hasStartTime() {
        if (startTime != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasEndTime() {
        if (endTime != null) {
            return true;
        }
        return false;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    /**
     * Indicates if this item is an event
     */
    @Override
    public boolean isEvent() {
        return (hasStartTime() && hasEndTime());
    }

    /**
     * Indicates if this item is a floatingTask
     */
    @Override
    public boolean isFloatingTask() {
        return (!hasStartTime() && !hasEndTime());
    }

    /**
     * Indicates if this item is a task
     */
    @Override
    public boolean isTask() {
        return (!hasStartTime() && hasEndTime());
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("d/" + this.getDescription().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        sb.append(((this.hasStartTime()) ? "s/" + this.getStartTime().value : "") + " ");
        sb.append(((this.hasEndTime()) ? "e/" + this.getEndTime().value : "") + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Compares the current TestTask with another TestTask other.
     * The current task is considered to be less than the other task if
     * 1) This item has a earlier start time associated
     * 2) both items are not events but this item has a later end time
     * 3) but this task has a lexicographically smaller name (useful when sorting tasks in testing)
     */
    @Override
    public int compareTo(ReadOnlyTask other) {
        return compareItems(other);
    }

    private int compareName(ReadOnlyTask other) {
        return this.getName().toString().compareTo(other.getName().toString());
    }

    public int compareItems(ReadOnlyTask other) {
        assertTrue(other.isEvent() || other.isFloatingTask() || other.isTask());

        if (this.isTask() && other.isTask()) {
            return compareName(other);
        } else if (this.isTask() && other.isEvent()) {
            return -1;
        } else if (this.isTask() && other.isFloatingTask()) {
            return -1;
        }

        if (this.isEvent() && other.isEvent()) {
            return compareName(other);
        } else if (this.isEvent() && other.isTask()) {
            return 1;
        } else if (this.isEvent() && other.isFloatingTask()) {
            return -1;
        }

        if (this.isFloatingTask() && other.isFloatingTask()) {
            return compareName(other);
        } else if (this.isFloatingTask() && other.isTask()) {
            return 1;
        } else if (this.isFloatingTask() && other.isEvent()) {
            return 1;
        }

        //Should never reach this
        return 0;
    }

}
