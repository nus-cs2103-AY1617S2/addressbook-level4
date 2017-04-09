package seedu.today.commons.events.storage;

import seedu.today.commons.events.model.TaskManagerChangedEvent;
import seedu.today.model.ReadOnlyTaskManager;

/**
 * Indicates that new task data has been read from file.
 */
public class ReadFromNewFileEvent extends TaskManagerChangedEvent {

    public ReadFromNewFileEvent(ReadOnlyTaskManager data) {
        super(data);
    }

}
