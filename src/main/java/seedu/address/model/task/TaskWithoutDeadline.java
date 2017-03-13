package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

public class TaskWithoutDeadline extends Task {
    public TaskWithoutDeadline(Name name, UniqueTagList tags) {
        super(name, tags, false);
    }

    public TaskWithoutDeadline(ReadOnlyTask source) {
        super(source);
    }
}
