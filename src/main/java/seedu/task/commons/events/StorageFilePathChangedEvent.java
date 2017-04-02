package seedu.task.commons.events;

public class StorageFilePathChangedEvent extends BaseEvent {

    public final String newStorageFilePath;

    public StorageFilePathChangedEvent(String filePath) {
        newStorageFilePath = filePath;
    }

    @Override
    public String toString() {
        return "new storage file path: " + newStorageFilePath;
    }
}
