package seedu.taskboss.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.taskboss.commons.core.ComponentManager;
import seedu.taskboss.commons.core.LogsCenter;
import seedu.taskboss.commons.events.model.TaskBossChangedEvent;
import seedu.taskboss.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskboss.commons.exceptions.DataConversionException;
import seedu.taskboss.model.ReadOnlyTaskBoss;
import seedu.taskboss.model.UserPrefs;

/**
 * Manages storage of TaskBoss data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskBossStorage taskBossStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskBossStorage taskBossStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskBossStorage = taskBossStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String taskBossFilePath, String userPrefsFilePath) {
        this(new XmlTaskBossStorage(taskBossFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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


    // ================ TaskBoss methods ==============================

    @Override
    public String getTaskBossFilePath() {
        return taskBossStorage.getTaskBossFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskBoss> readTaskBoss() throws DataConversionException, IOException {
        return readTaskBoss(taskBossStorage.getTaskBossFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskBoss> readTaskBoss(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskBossStorage.readTaskBoss(filePath);
    }

    @Override
    public void saveTaskBoss(ReadOnlyTaskBoss taskBoss) throws IOException {
        saveTaskBoss(taskBoss, taskBossStorage.getTaskBossFilePath());
    }

    @Override
    public void saveTaskBoss(ReadOnlyTaskBoss taskBoss, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskBossStorage.saveTaskBoss(taskBoss, filePath);
    }


    @Override
    @Subscribe
    public void handleTaskBossChangedEvent(TaskBossChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskBoss(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
