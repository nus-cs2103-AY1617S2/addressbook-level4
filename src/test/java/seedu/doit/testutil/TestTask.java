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


}
