package seedu.task.commons.events.storage;

import java.io.File;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskManager;

//@@author A0163848R
/**
 * Represents a request to export the stored YTomorrow task file.
 */
public class ExportRequestEvent extends BaseEvent {

    private ReadOnlyTaskManager toExport;
    private File target;

    public ExportRequestEvent(ReadOnlyTaskManager toExport, File target) {
        this.toExport = toExport;
        this.target = target;
    }

    /**
     * Get the YTomorrow task database to export
     * @return YTomorrow task database to export
     */
    public ReadOnlyTaskManager getYTomorrowToExport() {
        return toExport;
    }

    /**
     * Gets the file path to export to
     * @return File path to export to
     */
    public File getTargetFile() {
        return target;
    }

    @Override
    public String toString() {
        return "Exported YTomorrow: " + target.toString();
    }

}
