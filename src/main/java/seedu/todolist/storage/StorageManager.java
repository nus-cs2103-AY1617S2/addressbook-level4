package seedu.todolist.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.events.model.ToDoListChangedEvent;
import seedu.todolist.commons.events.storage.DataSavingExceptionEvent;
import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.model.ReadOnlyToDoList;
import seedu.todolist.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ToDoListStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ToDoListStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new XmlToDoListStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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
    public String getToDoListFilePath() {
        return addressBookStorage.getToDoListFilePath();
    }

    @Override
    public Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException {
        return readToDoList(addressBookStorage.getToDoListFilePath());
    }

    @Override
    public Optional<ReadOnlyToDoList> readToDoList(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readToDoList(filePath);
    }

    @Override
    public void saveToDoList(ReadOnlyToDoList addressBook) throws IOException {
        saveToDoList(addressBook, addressBookStorage.getToDoListFilePath());
    }

    @Override
    public void saveToDoList(ReadOnlyToDoList addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveToDoList(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(ToDoListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveToDoList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
