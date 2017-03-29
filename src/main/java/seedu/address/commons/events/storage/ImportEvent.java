package seedu.address.commons.events.storage;

import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.model.ReadOnlyTaskManager;

/**
 * Indicates that new task data has been read from file.
 */
public class ImportEvent extends TaskManagerChangedEvent {

    public ImportEvent(ReadOnlyTaskManager data) {
        super(data);
    }

}
