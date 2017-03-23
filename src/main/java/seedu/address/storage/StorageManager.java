package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.WhatsLeftChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of WhatsLeft data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private WhatsLeftStorage whatsLeftStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(WhatsLeftStorage whatsLeftStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.whatsLeftStorage = whatsLeftStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String whatsLeftFilePath, String userPrefsFilePath) {
        this(new XmlWhatsLeftStorage(whatsLeftFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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


    // ================ WhatsLeft methods ==============================

    @Override
    public String getWhatsLeftFilePath() {
        return whatsLeftStorage.getWhatsLeftFilePath();
    }

    @Override
    public Optional<ReadOnlyWhatsLeft> readWhatsLeft() throws DataConversionException, IOException {
        return readWhatsLeft(whatsLeftStorage.getWhatsLeftFilePath());
    }

    @Override
    public Optional<ReadOnlyWhatsLeft> readWhatsLeft(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return whatsLeftStorage.readWhatsLeft(filePath);
    }

    @Override
    public void saveWhatsLeft(ReadOnlyWhatsLeft whatsLeft) throws IOException {
        saveWhatsLeft(whatsLeft, whatsLeftStorage.getWhatsLeftFilePath());
    }

    @Override
    public void saveWhatsLeft(ReadOnlyWhatsLeft whatsLeft, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        whatsLeftStorage.saveWhatsLeft(whatsLeft, filePath);
    }


    @Override
    @Subscribe
    public void handleWhatsLeftChangedEvent(WhatsLeftChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveWhatsLeft(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
