package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.model.TaskManagerPathChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of TaskManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private Config config;
    private TaskManagerStorage taskManagerStorage;
    private UserPrefsStorage userPrefsStorage;
    private Stack<String> taskManagerStorageHistory;

    public StorageManager(Config config, TaskManagerStorage taskManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.config = config;
        this.taskManagerStorage = taskManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.taskManagerStorageHistory = new Stack<String>();
    }

    public StorageManager(Config config) {
        this(config, new XmlTaskManagerStorage(config.getTaskManagerFilePath()),
                new JsonUserPrefsStorage(config.getUserPrefsFilePath()));
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
    public String getTaskManagerFilePath() {
        return taskManagerStorage.getTaskManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskManagerStorage.readTaskManager(filePath);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskManagerStorage.saveTaskManager(taskManager, filePath);
    }

    @Override
    public void setTaskManagerFilePath(String filePath) {
        logger.fine("Setting task manager save location to: " + filePath);
        taskManagerStorage.setTaskManagerFilePath(filePath);
    }

    @Override
    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleTaskManagerPathChangedEvent(TaskManagerPathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data path changed, saving to file"));

        String path = event.path;
        String oldPath = getTaskManagerFilePath();
        if (path == null) {
            assert !taskManagerStorageHistory.isEmpty();
            path = taskManagerStorageHistory.pop();
        } else {
            taskManagerStorageHistory.add(oldPath);
        }

        try {
            setTaskManagerFilePath(path);
            saveTaskManager(event.data);
            config.setTaskManagerFilePath(path);
            config.save();

            if (!FileUtil.deleteFile(new File(oldPath))) {
                throw new IOException("File at " + oldPath + " cannot be deleted");
            }

        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
