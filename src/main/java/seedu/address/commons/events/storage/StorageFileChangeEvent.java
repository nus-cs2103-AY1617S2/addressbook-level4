//@@author A0144885R
package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for App termination
 */
public class StorageFileChangeEvent extends BaseEvent {

    public String filePath;

    public StorageFileChangeEvent(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
