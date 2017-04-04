package seedu.opus.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.opus.commons.core.ComponentManager;
import seedu.opus.commons.core.Config;
import seedu.opus.commons.core.LogsCenter;
import seedu.opus.commons.events.model.ChangeSaveLocationEvent;
import seedu.opus.commons.events.model.TaskManagerChangedEvent;
import seedu.opus.commons.events.storage.DataSavingExceptionEvent;
import seedu.opus.commons.exceptions.DataConversionException;
import seedu.opus.commons.util.ConfigUtil;
import seedu.opus.commons.util.StringUtil;
import seedu.opus.model.ReadOnlyTaskManager;
import seedu.opus.model.UserPrefs;

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

    //@@author A0148081H
    public StorageManager(TaskManagerStorage taskManagerStorage, UserPrefsStorage userPrefsStorage, Config config) {
        super();
        this.taskManagerStorage = taskManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.config = config;
    }
    //@@author

    public StorageManager(String taskManagerFilePath, String userPrefsFilePath) {
        this(new XmlTaskManagerStorage(taskManagerFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    //@@author A0148081H
    public StorageManager(String taskManagerFilePath, String userPrefsFilePath, Config config) {
        this(new XmlTaskManagerStorage(taskManagerFilePath), new JsonUserPrefsStorage(userPrefsFilePath), config);
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

    //@@author A0148081H
    @Override
    public void setTaskManagerFilePath(String filePath) {
        assert StringUtil.isValidPathToFile(filePath);
        taskManagerStorage.setTaskManagerFilePath(filePath);
        logger.info("Setting opus file path to: " + filePath);
    }

    private void saveConfigFile() {
        try {
            ConfigUtil.saveConfig(config, config.getConfigFilePath());
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
    }

    @Override
    @Subscribe
    public void handleChangeSaveLocationEvent(ChangeSaveLocationEvent event) {
        String location = event.location;

        setTaskManagerFilePath(location);
        config.setTaskManagerFilePath(location);
        saveConfigFile();

        logger.info(LogsCenter.getEventHandlingLogMessage(event));
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
}
