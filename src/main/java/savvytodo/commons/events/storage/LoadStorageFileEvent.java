package savvytodo.commons.events.storage;

import savvytodo.commons.events.BaseEvent;

/**
 * Loads the storage file in the given file path
 * @author A0147827U
 */
public class LoadStorageFileEvent extends BaseEvent {

    private String filePath;

    public LoadStorageFileEvent(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return filePath;
    }

    public String getFilePath() {
        return filePath;
    }

}
