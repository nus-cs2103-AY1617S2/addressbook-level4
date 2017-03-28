package seedu.todolist.testutil;

import java.util.Objects;

import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.EndTime;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.StartTime;
import seedu.todolist.model.task.Task;

/**
 * A mutable task object. For testing only.
 */
public class TestTask extends Task {

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

    @Override
    public void setName(Name name) {
        this.name = name;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(EndTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    // TODO
    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (this.startTime != null) {
            builder.append(" Start Time: " + getStartTime().toString());
        }
        if (this.endTime != null) {
            builder.append(" End Time: " + getEndTime().toString());
        }
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startTime, endTime, tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TestTask // instanceof handles nulls
                        && ((TestTask) other).getName().equals(this.getName())
                        && ((TestTask) other).getStartTime().equals(this.getStartTime())
                        && ((TestTask) other).getEndTime().equals(this.getEndTime())
                        && (((TestTask) other).isComplete() == this.isComplete()));
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

    @Override
    public String getType() {
        return "StartEndTask";
    }
}
