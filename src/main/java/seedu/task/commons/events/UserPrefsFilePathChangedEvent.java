package seedu.task.commons.events;

public class UserPrefsFilePathChangedEvent extends BaseEvent {

    public final String newStorageFilePath;

    public UserPrefsFilePathChangedEvent(String filePath) {
        newStorageFilePath = filePath;
    }

    @Override
    public String toString() {
        return "new user prefs file path: " + newStorageFilePath;
    }
}
