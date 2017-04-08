package onlythree.imanager.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import onlythree.imanager.commons.core.ComponentManager;
import onlythree.imanager.commons.core.LogsCenter;
import onlythree.imanager.commons.events.model.TaskListChangedEvent;
import onlythree.imanager.commons.events.storage.DataSavingExceptionEvent;
import onlythree.imanager.commons.events.storage.FileLocationChangedEvent;
import onlythree.imanager.commons.exceptions.DataConversionException;
import onlythree.imanager.model.ReadOnlyTaskList;
import onlythree.imanager.model.UserPrefs;

/**
 * Manages storage of TaskList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskListStorage taskListStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(TaskListStorage taskListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskListStorage = taskListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String taskListFilePath, String userPrefsFilePath) {
        this(new XmlTaskListStorage(taskListFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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

    //@@author A0148052L
    public void handleFileLocationChangedEvent(FileLocationChangedEvent event) throws
        DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "File Location is changed."));
        setTaskListFilePath(event.getFilePath());
        try {
            saveTaskList(event.getData());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
