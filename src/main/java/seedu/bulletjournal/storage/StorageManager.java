package seedu.bulletjournal.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.bulletjournal.commons.core.ComponentManager;
import seedu.bulletjournal.commons.core.LogsCenter;
import seedu.bulletjournal.commons.events.model.FilePathChangedEvent;
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
    private XmlTodoListStorage todoListStorage;
    private JsonUserPrefsStorage userPrefsStorage;

    public StorageManager(String taskListFilePath, String userPrefsFilePath) {
        super();
        this.todoListStorage = new XmlTodoListStorage(taskListFilePath);
        this.userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
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

    // ================ BulletJournal methods ==============================

    @Override
    public String getBulletJournalFilePath() {
        return todoListStorage.getBulletJournalFilePath();
    }

    @Override
    public Optional<ReadOnlyTodoList> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(todoListStorage.getBulletJournalFilePath());
    }

    @Override
    public Optional<ReadOnlyTodoList> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return todoListStorage.readAddressBook(filePath);
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList addressBook) throws IOException {
        saveTodoList(addressBook, todoListStorage.getBulletJournalFilePath());
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        todoListStorage.saveTodoList(addressBook, filePath);
    }

    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(TodoListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTodoList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleFilePathChangedEvent(FilePathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "File path changed"));
        todoListStorage.setBulletJournalFilePath(event.newFilePath);

    }

}
