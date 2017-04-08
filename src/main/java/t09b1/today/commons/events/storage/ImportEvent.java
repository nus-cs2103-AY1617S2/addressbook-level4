package t09b1.today.commons.events.storage;

import t09b1.today.commons.events.model.TaskManagerChangedEvent;
import t09b1.today.model.ReadOnlyTaskManager;

/**
 * Indicates that new task data has been read from file.
 */
public class ImportEvent extends TaskManagerChangedEvent {

    public ImportEvent(ReadOnlyTaskManager data) {
        super(data);
    }

}
