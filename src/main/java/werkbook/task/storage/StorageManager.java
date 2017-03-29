package werkbook.task.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import werkbook.task.commons.core.ComponentManager;
import werkbook.task.commons.core.Config;
import werkbook.task.commons.core.LogsCenter;
import werkbook.task.commons.events.model.TaskListChangedEvent;
import werkbook.task.commons.events.storage.DataSavingExceptionEvent;
import werkbook.task.commons.events.storage.TaskListStorageChangedEvent;
import werkbook.task.commons.exceptions.DataConversionException;
import werkbook.task.commons.util.ConfigUtil;
import werkbook.task.model.ReadOnlyTaskList;
import werkbook.task.model.UserPrefs;

/**
 * Manages storage of TaskList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private Config config;
    private TaskListStorage taskListStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(Config config) {
        this(config.getTaskListFilePath(), config.getUserPrefsFilePath());
        this.config = config;
    }

    public StorageManager(TaskListStorage taskListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskListStorage = taskListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String taskListFilePath, String userPrefsFilePath) {
        this(new XmlTaskListStorage(taskListFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    //@@author A0162266E
    // ================ Config methods =================================
    @Override
    public void setTaskListFilePath(Path filePath) throws IOException {
        this.taskListStorage = new XmlTaskListStorage(filePath.toString());
        config.setTaskListFilePath(filePath.toString());
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        raise(new TaskListStorageChangedEvent(filePath.toString()));
    }
    //@@author

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TaskList methods ==============================

    @Override
    public String getTaskListFilePath() {
        return taskListStorage.getTaskListFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        return readTaskList(taskListStorage.getTaskListFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskListStorage.readTaskList(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {
        saveTaskList(taskList, taskListStorage.getTaskListFilePath());
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskListStorage.saveTaskList(taskList, filePath);
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
