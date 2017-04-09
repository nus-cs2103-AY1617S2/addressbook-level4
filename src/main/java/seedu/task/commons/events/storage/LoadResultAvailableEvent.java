package seedu.task.commons.events.storage;

import java.io.File;
import java.util.Optional;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskManager;

//@@author A0163848R-reused
/**
 * Indicates that a new result is available.
 */
public class LoadResultAvailableEvent extends BaseEvent {

    private final Optional<ReadOnlyTaskManager> imported;
    private final File origin;

    public LoadResultAvailableEvent(Optional<ReadOnlyTaskManager> imported, File origin) {
        this.imported = imported;
        this.origin = origin;
    }

    public Optional<ReadOnlyTaskManager> getImported() {
        return imported;
    }

    public File getFile() {
        return origin;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
