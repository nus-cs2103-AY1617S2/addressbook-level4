package seedu.address.commons.events.ui;

import java.util.Optional;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

//@@author A0163848R
/**
 * Indicates that a new result is available.
 */
public class ImportResultAvailableEvent extends BaseEvent {

    private final Optional<ReadOnlyAddressBook> imported;

    public ImportResultAvailableEvent(Optional<ReadOnlyAddressBook> imported) {
        this.imported = imported;
    }

    public Optional<ReadOnlyAddressBook> getImported() {
        return imported;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
