package seedu.todolist.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.Config;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.events.model.TodoListChangedEvent;
import seedu.todolist.commons.events.storage.DataSavingExceptionEvent;
import seedu.todolist.commons.events.storage.SaveFilePathChangedEvent;
import seedu.todolist.commons.events.model.TodoListChangedEvent;
import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.commons.util.ConfigUtil;
import seedu.todolist.model.ReadOnlyTodoList;
import seedu.todolist.model.TodoList;
import seedu.todolist.model.UserPrefs;

/**
 * Manages storage of TodoList data in local storage.
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

    public StorageManager(String todoListFilePath, String userPrefsFilePath) {
        this(new XmlTodoListStorage(todoListFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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

    //@@author A0163720M
    /** Raises an event to indicate the save file path has changed
     * @throws IOException
     * @throws DataConversionException */
    private void indicateSaveFilePathChanged(String saveFilePath) throws DataConversionException, IOException {
        File f = new File(saveFilePath);
        ReadOnlyTodoList newData;
        
        // if the file is completely empty (i.e. just created), reset the list with a new list
        if (f.length() == 0) {
            newData = new TodoList();
        } else {
            newData = readTodoList(saveFilePath).get();
        }
        
        raise(new SaveFilePathChangedEvent(saveFilePath, newData));
    }
    //@@author

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
        logger.fine("Attempting to read data from file: " + filePath);
        return todoListStorage.readTodoList(filePath);
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList todoList) throws IOException {
        saveTodoList(todoList, todoListStorage.getTodoListFilePath());
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList todoList, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        todoListStorage.saveTodoList(todoList, filePath);
    }

    //@@author A0163720M
    public void updateSaveFilePath(String saveFilePath) throws DataConversionException, IOException {
        logger.fine("Attempting to update save file: " + saveFilePath);

        // There should only be one instance of config each session - grab a
        // handle on that specific one
        Config config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();
        config.setTodoListFilePath(saveFilePath);
        // Update config file in case it was missing to begin with or there
        // are new/unused fields
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        indicateSaveFilePathChanged(saveFilePath);
    }
    //@@author

    @Override
    @Subscribe
    public void handleTodoListChangedEvent(TodoListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTodoList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    //@@author A0163720M
    @Override
    @Subscribe
    public void handleSaveFilePathChangedEvent(SaveFilePathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Save file location changed"));
    }
    //@@author
}
