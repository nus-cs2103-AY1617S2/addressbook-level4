package seedu.address.commons.events.ui;

import java.util.Optional;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskManager;

//@@author A0163848R
/**
 * Indicates that a new result is available.
 */
public class ImportResultAvailableEvent extends BaseEvent {

    private final Optional<ReadOnlyTaskManager> imported;

    public ImportResultAvailableEvent(Optional<ReadOnlyTaskManager> imported) {
        this.imported = imported;
    }

    public Optional<ReadOnlyTaskManager> getImported() {
        return imported;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
