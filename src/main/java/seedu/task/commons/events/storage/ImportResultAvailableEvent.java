package seedu.task.commons.events.storage;

import java.util.Optional;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskManager;

//@@author A0163848R
/**
 * Indicates that a new result is available.
 */
public class ImportResultAvailableEvent extends BaseEvent {

    private final Optional<ReadOnlyTaskManager> imported;

    public ImportResultAvailableEvent(Optional<ReadOnlyTaskManager> imported) {
        this.imported = imported;
    }

    /**
     * Get the imported YTomorrow task database
     * @return Imported YTomorrow task database
     */
    public Optional<ReadOnlyTaskManager> getImported() {
        return imported;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
