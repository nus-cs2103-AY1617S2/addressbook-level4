package seedu.doit.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.doit.commons.core.ComponentManager;
import seedu.doit.commons.core.Config;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.events.model.TaskManagerChangedEvent;
import seedu.doit.commons.events.storage.DataSavingExceptionEvent;
import seedu.doit.commons.events.storage.TaskManagerLoadChangedEvent;
import seedu.doit.commons.events.storage.TaskManagerSaveChangedEvent;
import seedu.doit.commons.exceptions.DataConversionException;
import seedu.doit.commons.util.ConfigUtil;
import seedu.doit.model.ReadOnlyTaskManager;
import seedu.doit.model.UserPrefs;

/**
 * Manages storage of TaskManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskManagerStorage taskManagerStorage;
    private UserPrefsStorage userPrefsStorage;
    private Config config;

    public StorageManager(TaskManagerStorage taskManagerStorage, UserPrefsStorage userPrefsStorage, Config config) {
        super();
        this.taskManagerStorage = taskManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.config = config;
    }

    public StorageManager(String taskManagerFilePath, String userPrefsFilePath) {
        this(new XmlTaskManagerStorage(taskManagerFilePath), new JsonUserPrefsStorage(userPrefsFilePath), new Config());
        this.config.setTaskManagerFilePath(taskManagerFilePath);
        this.config.setUserPrefsFilePath(userPrefsFilePath);
    }

    public StorageManager(Config config) {
        this(config.getTaskManagerFilePath(), config.getUserPrefsFilePath());
    }

    public Config getConfig() {
        return this.config;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return this.userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        this.userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ TaskManager methods ==============================
    @Override
    public String getTaskManagerFilePath() {
        return this.taskManagerStorage.getTaskManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(this.taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return this.taskManagerStorage.readTaskManager(filePath);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, this.taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        this.taskManagerStorage.saveTaskManager(taskManager, filePath);
    }

    @Override
    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Local data changed, saving to file" + this.config.getTaskManagerFilePath()));
        try {
            saveTaskManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // @@author A0138909R
    @Override
    @Subscribe
    public void handleTaskManagerSaveChangedEvent(TaskManagerSaveChangedEvent event) {
        String newPath = event.getFilePath();
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Directory changed, saving to new directory at: " + newPath));
        String oldPath = this.config.getTaskManagerFilePath();
        this.config.setTaskManagerFilePath(newPath);
        setTaskManagerFilePath(newPath);
        try {
            copyTaskManager(oldPath, newPath);
            ConfigUtil.saveConfig(this.config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException ioe) {
            this.config.setTaskManagerFilePath(oldPath);
            setTaskManagerFilePath(oldPath);
            ioe.printStackTrace();
        }
        try {
            saveTaskManager(event.getData());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }

    }

    @Override
    @Subscribe
    public void handleTaskManagerLoadChangedEvent(TaskManagerLoadChangedEvent event) {
        String newPath = event.getFilePath();
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Directory changed, saving to new directory at: " + newPath));
        String oldPath = this.config.getTaskManagerFilePath();
        this.config.setTaskManagerFilePath(newPath);
        setTaskManagerFilePath(newPath);
        try {
            ConfigUtil.saveConfig(this.config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException ioe) {
            logger.warning("IOException thrown in Load Changed Event.");
            this.config.setTaskManagerFilePath(oldPath);
            setTaskManagerFilePath(oldPath);
            ioe.printStackTrace();
        }
    }

    @Override
    public void setTaskManagerFilePath(String filePath) {
        this.taskManagerStorage.setTaskManagerFilePath(filePath);
    }

    @Override
    public void copyTaskManager(String oldPath, String newPath) throws IOException {
        this.taskManagerStorage.copyTaskManager(oldPath, newPath);
    }
    // @@author
}
