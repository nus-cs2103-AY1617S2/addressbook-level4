package t15b1.taskcrusher.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import t15b1.taskcrusher.commons.core.ComponentManager;
import t15b1.taskcrusher.commons.core.LogsCenter;
import t15b1.taskcrusher.commons.events.model.AddressBookChangedEvent;
import t15b1.taskcrusher.commons.events.storage.DataSavingExceptionEvent;
import t15b1.taskcrusher.commons.exceptions.DataConversionException;
import t15b1.taskcrusher.model.ReadOnlyUserInbox;
import t15b1.taskcrusher.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

//    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
//        this(new XmlAddressBookStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
//    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new TextFileInboxStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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
        return addressBookStorage.getUserInboxFilePath();
    }

    @Override
    public Optional<ReadOnlyUserInbox> readUserInbox() throws DataConversionException, IOException {
        return readUserInbox(addressBookStorage.getUserInboxFilePath());
    }

    @Override
    public Optional<ReadOnlyUserInbox> readUserInbox(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readUserInbox(filePath);
    }

    @Override
    public void saveUserInbox(ReadOnlyUserInbox addressBook) throws IOException {
        saveUserInbox(addressBook, addressBookStorage.getUserInboxFilePath());
    }

    @Override
    public void saveUserInbox(ReadOnlyUserInbox addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveUserInbox(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveUserInbox(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
