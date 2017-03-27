package seedu.doist.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.doist.commons.core.ComponentManager;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.events.model.AliasListMapChangedEvent;
import seedu.doist.commons.events.model.TodoListChangedEvent;
import seedu.doist.commons.events.storage.DataSavingExceptionEvent;
import seedu.doist.commons.exceptions.DataConversionException;
import seedu.doist.model.ReadOnlyAliasListMap;
import seedu.doist.model.ReadOnlyTodoList;
import seedu.doist.model.UserPrefs;

/**
 * Manages storage of Doist data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TodoListStorage todoListStorage;
    private UserPrefsStorage userPrefsStorage;
    private AliasListMapStorage aliasListMapStorage;

    public StorageManager(TodoListStorage todoListStorage, AliasListMapStorage aliasListStorage,
                            UserPrefsStorage userPrefsStorage) {
        super();
        this.todoListStorage = todoListStorage;
        this.aliasListMapStorage = aliasListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String todoListFilePath, String aliasListMapFilePath, String userPrefsFilePath) {
        this(new XmlTodoListStorage(todoListFilePath), new XmlAliasListMapStorage(aliasListMapFilePath),
                new JsonUserPrefsStorage(userPrefsFilePath));
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

    // ================ TodoList methods ==============================

    @Override
    public String getTodoListFilePath() {
        return todoListStorage.getTodoListFilePath();
    }

    @Override
    public Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException {
        return readTodoList(todoListStorage.getTodoListFilePath());
    }

    @Override
    public Optional<ReadOnlyTodoList> readTodoList(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from todolist file: " + filePath);
        return todoListStorage.readTodoList(filePath);
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList todoList) throws IOException {
        saveTodoList(todoList, todoListStorage.getTodoListFilePath());
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList todoList, String filePath) throws IOException {
        logger.fine("Attempting to write to todolist data file: " + filePath);
        todoListStorage.saveTodoList(todoList, filePath);
    }

    // ================ ArrayListMap methods ==============================

    @Override
    public String getAliasListMapFilePath() {
        return aliasListMapStorage.getAliasListMapFilePath();
    }

    @Override
    public Optional<ReadOnlyAliasListMap> readAliasListMap() throws DataConversionException, IOException {
        return readAliasListMap(aliasListMapStorage.getAliasListMapFilePath());
    }

    @Override
    public Optional<ReadOnlyAliasListMap> readAliasListMap(String filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from aliaslistmap file: " + filePath);
        return aliasListMapStorage.readAliasListMap(filePath);
    }

    @Override
    public void saveAliasListMap(ReadOnlyAliasListMap aliasListMap) throws IOException {
        saveAliasListMap(aliasListMap, aliasListMapStorage.getAliasListMapFilePath());
    }

    @Override
    public void saveAliasListMap(ReadOnlyAliasListMap aliasListMap, String filePath) throws IOException {
        logger.fine("Attempting to write to aliaslistmap data file: " + filePath);
        aliasListMapStorage.saveAliasListMap(aliasListMap, filePath);
    }

    @Override
    @Subscribe
    public void handleTodoListChangedEvent(TodoListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data todolist changed, saving to file"));
        try {
            saveTodoList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleAliasListMapChangedEvent(AliasListMapChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data aliaslistmap changed, saving to file"));
        try {
            saveAliasListMap(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
