package seedu.doit.testutil;

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
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Description description;
    private StartTime startTime;
    private EndTime endTime;
    private boolean isDone;
    private Priority priority;
    private UniqueTagList tags;

    public TestTask() {
        this.tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.priority = taskToCopy.getPriority();
        this.startTime = taskToCopy.getStartTime();
        this.endTime = taskToCopy.getDeadline();
        this.isDone = false;
        this.description = taskToCopy.getDescription();
        this.tags = taskToCopy.getTags();
    }

    @Override
    public Name getName() {
        return this.name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Priority getPriority() {
        return this.priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public boolean getIsDone() {
        return this.isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public StartTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public EndTime getDeadline() {
        return this.endTime;
    }

    public void setDeadline(EndTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public Description getDescription() {
        return this.description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public boolean hasStartTime() {
        if (this.startTime == null || this.startTime.equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasEndTime() {
        if (this.endTime == null || this.endTime.equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public UniqueTagList getTags() {
        return this.tags;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    /**
     * Indicates if this item is an event
     */
    @Override
    public boolean isEvent() {
        return hasStartTime();
    }

    /**
     * Indicates if this item is a floatingTask
     */
    @Override
    public boolean isFloatingTask() {
        return !hasStartTime() && !hasEndTime();
    }

    /**
     * Indicates if this item is a task
     */
    @Override
    public boolean isTask() {
        return !hasStartTime() && hasEndTime();
    }

    /**
     * Returns 1 for task 2 for event 3 for floating tasks
     */
    @Override
    public int getItemType() {
        if (isTask()) {
            return 1;
        } else if (isEvent()) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + getName().fullName + " ");
        sb.append("d/" + getDescription().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        sb.append((this.hasStartTime() ? "s/" + this.getStartTime().value : "") + " ");
        sb.append((this.hasEndTime() ? "e/" + this.getDeadline().value : "") + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
