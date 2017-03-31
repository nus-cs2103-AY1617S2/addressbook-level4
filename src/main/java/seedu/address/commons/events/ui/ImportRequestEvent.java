package seedu.address.commons.events.ui;

import java.io.File;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.YTomorrow;

//@@author A0163848R
/**
* Represents a request to retrieve the file at the stored path.
*/
public class ImportRequestEvent extends BaseEvent {

    private YTomorrow toImport;
    private File target;
    
    public ImportRequestEvent(YTomorrow toImport, File target) {
        this.toImport = toImport;
        this.target = target;
    }
    
    public void writeYTomorrow(YTomorrow toWrite) {
        toImport.resetData(toWrite);;
    }
    
    public File getTargetFile() {
        return target;
    }

    @Override
    public String toString() {
        return "Imported YTomorrow: " + target.toString();
    }
}
