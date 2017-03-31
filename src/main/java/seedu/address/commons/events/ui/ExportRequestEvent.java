package seedu.address.commons.events.ui;

import java.io.File;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

//@@author A0163848R
/**
 * Represents a request to export the stored YTomorrow.
 */
public class ExportRequestEvent extends BaseEvent {
    
    private ReadOnlyAddressBook toExport;
    private File target;
    
    public ExportRequestEvent(ReadOnlyAddressBook toExport, File target) {
        this.toExport = toExport;
        this.target = target;
    }
    
    public ReadOnlyAddressBook getYTomorrowToExport() {
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
