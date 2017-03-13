package seedu.todolist.testutil;

import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.EndTime;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.StartTime;
import seedu.todolist.model.task.ReadOnlyTask;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private StartTime startTime;
    private EndTime endTime;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.startTime = taskToCopy.getStartTime();
        this.endTime = taskToCopy.getEndTime();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(EndTime endTime) {
        this.endTime = endTime;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    public StartTime getStartTime() {
        return startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    //TODO
    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        if (this.getStartTime() != null) {
            sb.append("s/" + this.getStartTime().toString() + " ");
        }
        if (this.getEndTime() != null) {
            sb.append("e/" + this.getEndTime().toString() + " ");
        }
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
