package seedu.address.commons.events.ui;

import java.io.File;
import java.util.Optional;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

//@@author A0163848R-reused
/**
 * Indicates that a new result is available.
 */
public class LoadResultAvailableEvent extends BaseEvent {

    private final Optional<ReadOnlyAddressBook> imported;
    private final File origin;

    public LoadResultAvailableEvent(Optional<ReadOnlyAddressBook> imported, File origin) {
        this.imported = imported;
        this.origin = origin;
    }

    public Optional<ReadOnlyAddressBook> getImported() {
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
