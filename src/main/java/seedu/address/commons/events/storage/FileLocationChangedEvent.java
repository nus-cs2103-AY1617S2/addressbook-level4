package seedu.address.commons.events.storage;


import seedu.address.commons.events.BaseEvent;

public class FileLocationChangedEvent extends BaseEvent {

    private String filePath;

    public FileLocationChangedEvent(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }


    @Override
    public String toString() {
        return "The file has been changed to" + filePath;
    }

}
