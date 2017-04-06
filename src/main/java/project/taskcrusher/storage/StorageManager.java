package project.taskcrusher.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import project.taskcrusher.commons.core.ComponentManager;
import project.taskcrusher.commons.core.LogsCenter;
import project.taskcrusher.commons.events.model.UserInboxChangedEvent;
import project.taskcrusher.commons.events.storage.DataSavingExceptionEvent;
import project.taskcrusher.commons.exceptions.DataConversionException;
import project.taskcrusher.model.ReadOnlyUserInbox;
import project.taskcrusher.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserInboxStorage userInboxStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(UserInboxStorage userInboxStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.userInboxStorage = userInboxStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new XmlUserInboxStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ AddressBook methods ==============================

    @Override
    public String getUserInboxFilePath() {
        return userInboxStorage.getUserInboxFilePath();
    }

    @Override
    public void loadNewStorageWith(String storageFilename) {
        this.userInboxStorage = new XmlUserInboxStorage(storageFilename);
    }

    @Override
    public Optional<ReadOnlyUserInbox> readUserInbox() throws DataConversionException, IOException {
        return readUserInbox(userInboxStorage.getUserInboxFilePath());
    }

    @Override
    public Optional<ReadOnlyUserInbox> readUserInbox(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userInboxStorage.readUserInbox(filePath);
    }

    @Override
    public void saveUserInbox(ReadOnlyUserInbox userInbox) throws IOException {
        saveUserInbox(userInbox, userInboxStorage.getUserInboxFilePath());
    }

    @Override
    public void saveUserInbox(ReadOnlyUserInbox userInbox, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userInboxStorage.saveUserInbox(userInbox, filePath);
    }


    @Override
    @Subscribe
    public void handleUserInboxChangedEvent(UserInboxChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveUserInbox(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
