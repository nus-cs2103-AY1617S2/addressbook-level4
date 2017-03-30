package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskList;

public class FileLocationChangedEvent extends BaseEvent {

    private String filePath;
    private ReadOnlyTaskList data;

    public FileLocationChangedEvent(String filePath, ReadOnlyTaskList data) {
        this.filePath = filePath;
        this.data = data;
    }

    public String getFilePath() {
        return filePath;
    }

    public ReadOnlyTaskList getData() {
        return data;
    }

    @Override
    public String toString() {
        return "The file has been changed to" + filePath;
    }

}
