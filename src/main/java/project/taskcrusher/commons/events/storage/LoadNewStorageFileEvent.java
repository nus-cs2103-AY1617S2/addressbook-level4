package project.taskcrusher.commons.events.storage;

import project.taskcrusher.commons.events.BaseEvent;

/** raised by load command as the user specifies new xml file to use for data storage.
 *  handled by MainApp.
 */
public class LoadNewStorageFileEvent extends BaseEvent {
    public static final String MESSAGE_LOAD_FAILED = "Loading of new storage file unsuccessful";
    public final String filePathToLoad;

    public LoadNewStorageFileEvent(String filePathToLoad) {
        this.filePathToLoad = filePathToLoad;
    }

    @Override
    public String toString() {
        return null;
    }

}
