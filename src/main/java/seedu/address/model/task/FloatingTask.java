package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

//@@author A0163848R
/**
 * Represents a Floating Task in the YTommorow.
 */
public class FloatingTask extends Task {
    public FloatingTask(Name name, Email email, Group group, UniqueTagList tags) {
        super(name, null, null, email, group, tags);
    }
}
