package seedu.task.commons.events.storage;

import java.io.File;

import seedu.task.commons.events.BaseEvent;

//@@author A0163848R
/**
* Represents a request to retrieve the file at the stored path.
*/
public class ImportRequestEvent extends BaseEvent {

    private File target;

    public ImportRequestEvent(File target) {
        this.target = target;
    }

    /**
     * Get file path to import from
     * @return file path to import from
     */
    public File getTargetFile() {
        return target;
    }

    @Override
    public String toString() {
        return "Imported YTomorrow: " + target.toString();
    }
}
