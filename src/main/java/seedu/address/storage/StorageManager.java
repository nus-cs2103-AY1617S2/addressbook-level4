package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ToDoAppChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyToDoApp;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ToDoAppStorage toDoAppStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ToDoAppStorage toDoAppStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.toDoAppStorage = toDoAppStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String toDoAppFilePath, String userPrefsFilePath) {
        this(new XmlToDoAppStorage(toDoAppFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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
    public String getToDoAppFilePath() {
        return toDoAppStorage.getToDoAppFilePath();
    }

    @Override
    public Optional<ReadOnlyToDoApp> readToDoApp() throws DataConversionException, IOException {
        return readToDoApp(toDoAppStorage.getToDoAppFilePath());
    }

    @Override
    public Optional<ReadOnlyToDoApp> readToDoApp(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return toDoAppStorage.readToDoApp(filePath);
    }

    @Override
    public void saveToDoApp(ReadOnlyToDoApp toDoApp) throws IOException {
        saveToDoApp(toDoApp, toDoAppStorage.getToDoAppFilePath());
    }

    @Override
    public void saveToDoApp(ReadOnlyToDoApp toDoApp, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        toDoAppStorage.saveToDoApp(toDoApp, filePath);
    }


    @Override
    @Subscribe
    public void handleToDoAppChangedEvent(ToDoAppChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveToDoApp(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
