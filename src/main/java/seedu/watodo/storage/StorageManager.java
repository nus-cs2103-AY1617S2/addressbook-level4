package seedu.watodo.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.watodo.commons.core.ComponentManager;
import seedu.watodo.commons.core.LogsCenter;
import seedu.watodo.commons.events.model.TaskListChangedEvent;
import seedu.watodo.commons.events.storage.DataSavingExceptionEvent;
import seedu.watodo.commons.exceptions.DataConversionException;
import seedu.watodo.model.ReadOnlyTaskManger;
import seedu.watodo.model.UserPrefs;

/**
 * Manages storage of TaskManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskListStorage TaskListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskListStorage TaskListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.TaskListStorage = TaskListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String TaskListFilePath, String userPrefsFilePath) {
        this(new XmlTaskListStorage(TaskListFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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


    // ================ TaskManager methods ==============================

    @Override
    public String getTaskListFilePath() {
        return TaskListStorage.getTaskListFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskManger> readTaskList() throws DataConversionException, IOException {
        return readTaskList(TaskListStorage.getTaskListFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManger> readTaskList(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return TaskListStorage.readTaskList(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskManger TaskList) throws IOException {
        saveTaskList(TaskList, TaskListStorage.getTaskListFilePath());
    }

    @Override
    public void saveTaskList(ReadOnlyTaskManger TaskList, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        TaskListStorage.saveTaskList(TaskList, filePath);
    }


    @Override
    @Subscribe
    public void handleTaskListChangedEvent(TaskListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
