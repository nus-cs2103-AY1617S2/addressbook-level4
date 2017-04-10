//@@author A0139248X
package seedu.ezdo.commons.events.storage;

import seedu.ezdo.commons.events.BaseEvent;

/**
 * Indicates a request to change the directory of the saved ezDo.xml
 */
public class EzDoDirectoryChangedEvent extends BaseEvent {

    private static final String UPDATE_DIRECTORY = "New save directory: ";
    private String path;

    public EzDoDirectoryChangedEvent(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return UPDATE_DIRECTORY + path;
    }
}
