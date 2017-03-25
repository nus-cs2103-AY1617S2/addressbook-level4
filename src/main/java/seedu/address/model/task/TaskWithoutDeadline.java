package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

public class TaskWithoutDeadline extends Task {
    public TaskWithoutDeadline(Name name, UniqueTagList tags, boolean isDone) {
        super(name, tags, isDone);
    }

    public TaskWithoutDeadline(ReadOnlyTask source) {
        super(source);
        today = source.isToday();
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.TaskWithNoDeadline;
    }

    @Override
    public String getTaskDateTime() {
        return "";
    }

    @Override
    public int compareTo(ReadOnlyTask task2) {
        if (task2 instanceof TaskWithDeadline) {
            return -MAX_TIME_DIFF;
        } else {
            return 0;
        }
    }

    @Override
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setTags(replacement.getTags());
        this.setDone(replacement.isDone());
    }

    @Override
    public DateTime getDeadline() {
        return null;
    }

    @Override
    public String getTaskAbsoluteDateTime() {
        return "";
    }

    @Override
    public DateTime getStartingTime() {
        return null;
    }
}
