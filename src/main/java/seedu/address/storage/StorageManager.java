package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.ui.ExportRequestEvent;
import seedu.address.commons.events.ui.ImportRequestEvent;
import seedu.address.commons.events.ui.ImportResultAvailableEvent;
import seedu.address.commons.events.ui.LoadRequestEvent;
import seedu.address.commons.events.ui.LoadResultAvailableEvent;
import seedu.address.commons.events.ui.TargetFileRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
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
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
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
        Optional<ReadOnlyAddressBook> loaded = null;
        
        try {
            loaded = readAddressBook(ire.getTargetFile().getPath());
        } catch (DataConversionException | IOException e) {
        }
        
        raise(new LoadResultAvailableEvent(loaded, ire.getTargetFile()));
    }
    
    @Override
    @Subscribe
    public void handleImportRequestEvent(ImportRequestEvent ire) {
        Optional<ReadOnlyAddressBook> loaded = null;
        
        try {
            loaded = readAddressBook(ire.getTargetFile().getPath());
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
