package seedu.bulletjournal.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.bulletjournal.commons.core.ComponentManager;
import seedu.bulletjournal.commons.core.LogsCenter;
import seedu.bulletjournal.commons.events.model.TodoListChangedEvent;
import seedu.bulletjournal.commons.events.storage.DataSavingExceptionEvent;
import seedu.bulletjournal.commons.exceptions.DataConversionException;
import seedu.bulletjournal.model.ReadOnlyTodoList;
import seedu.bulletjournal.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TodoListStorage todoListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TodoListStorage todoListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.todoListStorage = todoListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new XmlAddressBookStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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
    public String getAddressBookFilePath() {
        return todoListStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTodoList> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(todoListStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTodoList> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return todoListStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyTodoList addressBook) throws IOException {
        saveAddressBook(addressBook, todoListStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyTodoList addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        todoListStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(TodoListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
