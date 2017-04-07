package seedu.task.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.Config;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.model.FilePathChangedEvent;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.storage.DataSavingExceptionEvent;
import seedu.task.commons.events.storage.UpdateUserPrefsEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.UserPrefs;

/**
 * Manages storage of TaskManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskManagerStorage taskManagerStorage;
    private UserPrefsStorage userPrefsStorage;
    private Config config;

    public StorageManager(TaskManagerStorage taskManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskManagerStorage = taskManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String taskManagerFilePath, String userPrefsFilePath) {
        this(new XmlTaskManagerStorage(taskManagerFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    public StorageManager(Config config) {
        this(config.getTaskManagerFilePath(), config.getUserPrefsFilePath());
        this.config = config;
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

    // @@author A0142487Y
    @Override
    public void setThemeTo(String themeName) {
        Optional<UserPrefs> optionalUserPrefs = null;
        try {
            optionalUserPrefs = this.readUserPrefs();
        } catch (DataConversionException e) {
            logger.warning("Failed to load the user preference file : " + StringUtil.getDetails(e));
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Theme of KIT will not be changed");
        }
        UserPrefs userPrefs = optionalUserPrefs.get();
        userPrefs.setTheme(themeName);
        try {
            this.saveUserPrefs(userPrefs);
            raise(new UpdateUserPrefsEvent(userPrefs));
        } catch (IOException e) {
            logger.warning("Failed to save new theme to user preference file : " + StringUtil.getDetails(e));
        }

    }
  //@@author

    // ================ TaskManager methods ==============================

    @Override
    public String getTaskManagerFilePath() {
        return taskManagerStorage.getTaskManagerFilePath();
    }

    @Override
    public void setTaskManagerFilePath(String path) {
        taskManagerStorage.setTaskManagerFilePath(path);
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

    //@@author A0140063X
    @Override
    public void saveBackup(String backupFilePath) throws IOException, FileNotFoundException {
        logger.fine("Attempting to backup data from " + backupFilePath);
        taskManagerStorage.saveBackup(backupFilePath);
    }

    //@@author A0142939W
    @Override
    @Subscribe
    public void handleFilePathChangedEvent(FilePathChangedEvent event) {
        config.setTaskManagerFilePath(event.path);
        try {
            taskManagerStorage.setTaskManagerFilePath(event.path);
            taskManagerStorage.saveTaskManager(event.taskManager, event.path);
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException ie) {
            logger.warning("Unable to save config file");
        }
    }

    //@@author A0140063X
    @Override
    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            if (!event.backupFilePath.trim().equals("")) {
                saveBackup(event.backupFilePath);
            }
            saveTaskManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    //@@author

}
