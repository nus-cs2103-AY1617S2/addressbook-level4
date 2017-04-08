package seedu.whatsleft.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.whatsleft.commons.core.ComponentManager;
import seedu.whatsleft.commons.core.Config;
import seedu.whatsleft.commons.core.LogsCenter;
import seedu.whatsleft.commons.events.model.ConfigChangedEvent;
import seedu.whatsleft.commons.events.model.WhatsLeftChangedEvent;
import seedu.whatsleft.commons.events.storage.DataSavingExceptionEvent;
import seedu.whatsleft.commons.exceptions.DataConversionException;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;
import seedu.whatsleft.model.UserPrefs;

/**
 * Manages storage of WhatsLeft data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private WhatsLeftStorage whatsLeftStorage;
    private UserPrefsStorage userPrefsStorage;
    private UserConfigStorage userConfigStorage;

    public StorageManager(WhatsLeftStorage whatsLeftStorage, UserPrefsStorage userPrefsStorage,
            UserConfigStorage userConfigStorage) {
        super();
        this.whatsLeftStorage = whatsLeftStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userConfigStorage = userConfigStorage;
    }

    public StorageManager(String whatsLeftFilePath, String userPrefsFilePath, String userConfigStorage) {
        this(new XmlWhatsLeftStorage(whatsLeftFilePath), new JsonUserPrefsStorage(userPrefsFilePath),
                new JsonUserConfigStorage(userConfigStorage));
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
    public void setWhatsLeftFilePath(String filepath) {
        whatsLeftStorage.setWhatsLeftFilePath(filepath);
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

    @Subscribe
    public void handleConfigChangedEvent(ConfigChangedEvent cce) {
        String newLocation = cce.data.getWhatsLeftFilePath();
        logger.info(LogsCenter.getEventHandlingLogMessage(cce, "Setting default filepath to " + newLocation));
        setWhatsLeftFilePath(newLocation);
    }
    // ================ WhatsLeft methods ==============================
    @Override
    public Optional<Config> readUserConfig() throws DataConversionException, IOException {
        logger.fine("Attempting to read configuration file");
        return userConfigStorage.readUserConfig();
    }

    @Override
    public void saveUserConfig(Config config) throws IOException {
        logger.fine("Attempting to save configuration file");
        userConfigStorage.saveUserConfig(config);
    }
}
