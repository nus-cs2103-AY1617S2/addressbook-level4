package seedu.task.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.storage.DataSavingExceptionEvent;
import seedu.task.commons.events.ui.ExportRequestEvent;
import seedu.task.commons.events.ui.ImportRequestEvent;
import seedu.task.commons.events.ui.ImportResultAvailableEvent;
import seedu.task.commons.events.ui.LoadRequestEvent;
import seedu.task.commons.events.ui.LoadResultAvailableEvent;
import seedu.task.commons.events.ui.TargetFileRequestEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskManagerStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskManagerStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new XmlAddressBookStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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


    // ================ AddressBook methods ==============================

    @Override
    public String getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException {
        return readTaskManager(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readTaskManager(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskManager addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    //@@author A0163848R
    @Override
    @Subscribe
    public void handleExportRequestEvent(ExportRequestEvent ere) {
        try {
            saveAddressBook(ere.getYTomorrowToExport(), ere.getTargetFile().getPath());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleLoadRequestEvent(LoadRequestEvent ire) {
        Optional<ReadOnlyTaskManager> loaded = null;

        try {
            loaded = readTaskManager(ire.getTargetFile().getPath());
        } catch (DataConversionException | IOException e) {
        }

        raise(new LoadResultAvailableEvent(loaded, ire.getTargetFile()));
    }

    @Override
    @Subscribe
    public void handleImportRequestEvent(ImportRequestEvent ire) {
        Optional<ReadOnlyTaskManager> loaded = null;

        try {
            loaded = readTaskManager(ire.getTargetFile().getPath());
        } catch (DataConversionException | IOException e) {
        }

        raise(new ImportResultAvailableEvent(loaded));
    }

    @Override
    @Subscribe
    public void handleTargetFileRequestEvent(TargetFileRequestEvent tfre) {
        addressBookStorage.setAddressBookFilePath(tfre.getTargetFile().getPath());
        tfre.getUserPrefs().getGuiSettings().setLastLoadedYTomorrow(tfre.getTargetFile().getPath());
    }

    @Override
    public void setAddressBookFilePath(String path) {
        addressBookStorage.setAddressBookFilePath(path);
    }

}
