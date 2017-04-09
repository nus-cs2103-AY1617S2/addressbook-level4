//@@author A0142255M

package seedu.tache.commons.events.storage;

import seedu.tache.commons.events.BaseEvent;

/**
 * Indicates that the directory of the data file has been changed.
 */
public class DataFileLocationChangedEvent extends BaseEvent {

    public String newLocation;

    public DataFileLocationChangedEvent(String newLocation) {
        assert newLocation != null;
        this.newLocation = newLocation;
    }

    @Override
    public String toString() {
        return newLocation;
    }

}
