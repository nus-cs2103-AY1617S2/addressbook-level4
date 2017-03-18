package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

public class TaskWithoutDeadline extends Task {
    public TaskWithoutDeadline(Name name, UniqueTagList tags, boolean isDone) {
        super(name, tags, isDone);
    }

    public TaskWithoutDeadline(ReadOnlyTask source) {
        super(source);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.TaskWithNoDeadline;
    }

    @Override
    public String getTaskDateTime() {
        return "";
    }
}
