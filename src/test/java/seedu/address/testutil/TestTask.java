package seedu.address.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Information;
import seedu.task.model.task.PriorityLevel;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.TaskName;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskName name;
    private Deadline date;
    private PriorityLevel priority;
    private Information info;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getTaskName();
        this.date = taskToCopy.getDate();
        this.priority = taskToCopy.getPriority();
        this.info = taskToCopy.getInfo();
        this.tags = taskToCopy.getTags();
    }

    public void setName(TaskName name) {
        this.name = name;
    }

    public void setDeadline(Deadline date) {
        this.date = date;
    }

    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    public void setInformation(Information info) {
        this.info = info;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public TaskName getTaskName() {
        return name;
    }

    @Override
    public Deadline getDate() {
        return date;
    }

    @Override
    public PriorityLevel getPriority() {
        return priority;
    }

    @Override
    public Information getInfo() {
        return info;
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
        sb.append("add " + this.getTaskName().taskName + " ");
        sb.append("i/" + this.getInfo().value + " ");
        sb.append("d/" + this.getDate().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
