package seedu.doit.testutil;

import seedu.doit.model.item.Description;
import seedu.doit.model.item.EndTime;
import seedu.doit.model.item.Name;
import seedu.doit.model.item.Priority;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.StartTime;
import seedu.doit.model.item.Task;
import seedu.doit.model.tag.UniqueTagList;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask, Comparable<TestTask> {

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
    public int compareTo(TestTask other) {
        int comparedStartTime = compareStartTime(other);
        if (comparedStartTime != 0) {
            return comparedStartTime;
        }

        int comparedEndTime = compareEndTime(other);
        if (comparedEndTime != 0) {
            return comparedEndTime;
        }

        return compareName(other);
    }

    private int compareName(TestTask other) {
        return this.getName().toString().compareTo(other.getName().toString());
    }

    public int compareStartTime(TestTask other) {
        if (this.hasStartTime() && other.hasStartTime()) {
            return compareName(other);
        } else if (this.hasStartTime()) {
            return 1;
        } else if (other.hasStartTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    public int compareEndTime(TestTask other) {
        if (this.hasEndTime() && other.hasEndTime()) {
            return compareName(other);
        } else if (this.hasEndTime()) {
            return -1;
        } else if (other.hasEndTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Compares the current TestTask with another TestTask other.
     * The current task is considered to be less than the other task if
     * 1) This item has a earlier start time associated
     * 2) both items are not events but this item has a later end time
     * 3) but this task has a lexicographically smaller name (useful when sorting tasks in testing)
     */
    @Override
    public int compareTo(Task other) {
        int comparedStartTime = compareStartTime(other);
        if (comparedStartTime != 0) {
            return comparedStartTime;
        }

        int comparedEndTime = compareEndTime(other);
        if (comparedEndTime != 0) {
            return comparedEndTime;
        }

        return compareName(other);
    }

    private int compareName(Task other) {
        return this.getName().toString().compareTo(other.getName().toString());
    }

    public int compareStartTime(Task other) {
        if (this.hasStartTime() && other.hasStartTime()) {
            return compareName(other);
        } else if (this.hasStartTime()) {
            return 1;
        } else if (other.hasStartTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    public int compareEndTime(Task other) {
        if (this.hasEndTime() && other.hasEndTime()) {
            return compareName(other);
        } else if (this.hasEndTime()) {
            return -1;
        } else if (other.hasEndTime()) {
            return 1;
        } else {
            return 0;
        }
    }

}
