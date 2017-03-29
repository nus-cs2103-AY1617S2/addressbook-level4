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
import seedu.address.commons.events.model.TaskManagerExportEvent;
import seedu.address.commons.events.model.TaskManagerImportEvent;
import seedu.address.commons.events.model.TaskManagerPathChangedEvent;
import seedu.address.commons.events.model.TaskManagerUseNewPathEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.ImportEvent;
import seedu.address.commons.events.storage.ReadFromNewFileEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;

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
        logger.fine("Attempting to read data from file: " + taskManagerStorage.getTaskManagerFilePath());
        return taskManagerStorage.readTaskManager(taskManagerStorage.getTaskManagerFilePath());
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
        path = getFromBackupIfNull(path, oldPath);

        try {
            setTaskManagerFilePath(path);
            saveTaskManager(event.data);
            setAndSaveConfig(path);

            deleteExtraCopy(oldPath);

        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleTaskManagerExportEvent(TaskManagerExportEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Exporting data to file"));

        String path = event.path;
        path = getFromBackupIfNull(path, path);

        try {
            if (event.path != null) {
                saveTaskManager(event.data, path);
            } else {
                deleteExtraCopy(path);
            }

        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    /**
     * If path is null, it sets it to the last seen value. Otherwise, it saves
     * currPath.
     *
     * @param path
     *            is set to last seen path if null
     * @param currPath
     *            is saved to history for future use
     * @return
     */
    private String getFromBackupIfNull(String path, String currPath) {
        if (path == null) {
            assert !taskManagerStorageHistory.isEmpty();
            path = taskManagerStorageHistory.pop();
        } else {
            taskManagerStorageHistory.add(currPath);
        }
        return path;
    }

    private void setAndSaveConfig(String path) throws IOException {
        config.setTaskManagerFilePath(path);
        config.save();
    }

    private void deleteExtraCopy(String path) throws IOException {
        if (!FileUtil.deleteFile(new File(path))) {
            throw new IOException("File at " + path + " cannot be deleted");
        }
    }

    @Override
    @Subscribe
    public void handleTaskManagerImportEvent(TaskManagerImportEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Importing from file"));
        indicateImportFile(event.path);
    }

    @Override
    @Subscribe
    public void handleTaskManagerUseNewPathEvent(TaskManagerUseNewPathEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data path changed, reading from file"));

        String path = event.path;
        String oldPath = getTaskManagerFilePath();
        path = getFromBackupIfNull(path, oldPath);

        try {
            setTaskManagerFilePath(path);
            setAndSaveConfig(path);
            indicateReadFromNewFile();
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    /**
     * Raises an event to indicate that a new data file is to be imported.
     */
    private void indicateImportFile(String path) {
        ReadOnlyTaskManager taskManager = getInitialData(path);
        raise(new ImportEvent(taskManager));
    }

    /**
     * Raises an event to indicate that a new data file has been read.
     */
    private void indicateReadFromNewFile() {
        ReadOnlyTaskManager taskManager = getInitialData(taskManagerStorage.getTaskManagerFilePath());
        raise(new ReadFromNewFileEvent(taskManager));
    }

    @Override
    public ReadOnlyTaskManager getInitialData(String path) {
        Optional<ReadOnlyTaskManager> taskManagerOptional;
        ReadOnlyTaskManager initialData;
        try {
            if (path == null) {
                path = taskManagerStorage.getTaskManagerFilePath();
            }
            taskManagerOptional = readTaskManager(path);
            if (!taskManagerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample TaskManager");
            }
            initialData = taskManagerOptional.orElseGet(SampleDataUtil::getSampleTaskManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TaskManager");
            initialData = new TaskManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskManager");
            initialData = new TaskManager();
        }
        return initialData;
    }

}
