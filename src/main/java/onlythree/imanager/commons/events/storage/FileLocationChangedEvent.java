//@@author A0148052L

package onlythree.imanager.commons.events.storage;

import onlythree.imanager.commons.events.BaseEvent;
import onlythree.imanager.model.ReadOnlyTaskList;

/**
 * Indicates the file location has been changed
 */
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
