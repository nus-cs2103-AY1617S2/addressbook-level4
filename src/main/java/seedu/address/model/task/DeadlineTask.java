package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

//@@author A0163848R
/**
 * Represents a Deadline Task in the YTomorrow.
 */
public class DeadlineTask extends Task {
    public DeadlineTask(Name name, EndDate end, Group group, UniqueTagList tags) {
        super(name, null, end, group, tags);
    }
}
