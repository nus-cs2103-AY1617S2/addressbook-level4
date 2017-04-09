package seedu.address.commons.events.ui;

import java.io.File;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskManager;

//@@author A0163848R
/**
 * Represents a request to export the stored YTomorrow.
 */
public class ExportRequestEvent extends BaseEvent {

    private ReadOnlyTaskManager toExport;
    private File target;

    public ExportRequestEvent(ReadOnlyTaskManager toExport, File target) {
        this.toExport = toExport;
        this.target = target;
    }

    public ReadOnlyTaskManager getYTomorrowToExport() {
        return toExport;
    }

    public File getTargetFile() {
        return target;
    }

    @Override
    public String toString() {
        return "Exported YTomorrow: " + target.toString();
    }

}
