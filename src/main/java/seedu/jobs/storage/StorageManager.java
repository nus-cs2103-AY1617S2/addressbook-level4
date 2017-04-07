package seedu.jobs.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.jobs.commons.core.ComponentManager;
import seedu.jobs.commons.core.LogsCenter;
import seedu.jobs.commons.events.model.TaskBookChangedEvent;
import seedu.jobs.commons.events.storage.ConfigChangeSavePathEvent;
import seedu.jobs.commons.events.storage.DataSavingExceptionEvent;
import seedu.jobs.commons.events.storage.SavePathChangedEvent;
import seedu.jobs.commons.events.storage.SavePathChangedEventException;
import seedu.jobs.commons.exceptions.DataConversionException;
import seedu.jobs.model.LoginInfo;
import seedu.jobs.model.ReadOnlyTaskBook;
import seedu.jobs.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskBookStorage taskBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private LoginInfoStorage loginInfoStorage;

    public StorageManager(TaskBookStorage taskBookStorage, UserPrefsStorage userPrefsStorage,
            LoginInfoStorage loginInfoStorage) {
        super();
        this.taskBookStorage = taskBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.loginInfoStorage = loginInfoStorage;
    }

    public StorageManager(String taskBookFilePath, String userPrefsFilePath, String loginInfoFilePath) {
        this(new XmlTaskBookStorage(taskBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath),
                new JsonLoginInfoStorage(loginInfoFilePath));
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


    // ================ TaskBook methods ==============================

    @Override
    public String getTaskBookFilePath() {
        return taskBookStorage.getTaskBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(taskBookStorage.getTaskBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskBookStorage.readTaskBook(filePath);
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, taskBookStorage.getTaskBookFilePath());
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskBookStorage.saveTaskBook(taskBook, filePath);
    }


    @Override
    @Subscribe
    public void handleTaskBookChangedEvent(TaskBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    @Subscribe
    public void handleSavePathChangedEvent(SavePathChangedEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        try {
            saveTaskBook(event.getData(), event.getPath());
            setFilePath(event.getPath());
            raise(new ConfigChangeSavePathEvent(event.getPath()));
        } catch (IOException e) {
            raise(new SavePathChangedEventException());
        }
    }
 // ================ LoginInfo methods ==============================

    @Override
    public Optional<LoginInfo> readLoginInfo() throws DataConversionException, IOException {
        return loginInfoStorage.readLoginInfo();
    }

    @Override
    public void saveLoginInfo(LoginInfo loginInfo) throws IOException {
        loginInfoStorage.saveLoginInfo(loginInfo);
    }

    @Override
    public void setFilePath(String filePath) {
        taskBookStorage.setFilePath(filePath);
    }
}
