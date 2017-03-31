package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TaskListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.FileLocationChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of TaskList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskListStorage taskListStorage;
    private UserPrefsStorage userPrefsStorage;
    private ConfigStorage configStorage;

    public StorageManager(TaskListStorage taskListStorage, UserPrefsStorage userPrefsStorage,
                          ConfigStorage configStorage) {
        super();
        this.taskListStorage = taskListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.configStorage = configStorage;
    }

    public StorageManager(String taskListFilePath, String userPrefsFilePath, String configFilePath) {
        this(new XmlTaskListStorage(taskListFilePath), new JsonUserPrefsStorage(userPrefsFilePath),
             new JsonConfigStorage(configFilePath));
    }

    // ================ Config methods ==============================

    @Override
    public Config readConfig() throws DataConversionException, IOException {
        return configStorage.readConfig();
    }

    @Override
    public void saveConfig(Config config) throws IOException {
        configStorage.saveConfig(config);
    }

    private void saveFilePathInConfig(String filePath) throws DataConversionException, IOException {
        Config thisConfig = configStorage.readConfig();
        thisConfig.setTaskListFilePath(filePath);
        configStorage.saveConfig(thisConfig);
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

    public void setTaskListFilePath(String filePath) {
        taskListStorage.setTaskListFilePath(filePath);
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

//@@Liu Yulin A0148052L
    public void handleFileLocationChangedEvent(FileLocationChangedEvent event) throws
        DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "File Location is changed."));
        setTaskListFilePath(event.getFilePath());
        try {
            saveTaskList(event.getData());
            saveFilePathInConfig(event.getFilePath());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
