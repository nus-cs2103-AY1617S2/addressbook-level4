package seedu.geekeep.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.geekeep.commons.core.ComponentManager;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.commons.events.model.GeeKeepChangedEvent;
import seedu.geekeep.commons.events.storage.DataSavingExceptionEvent;
import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.model.ReadOnlyGeeKeep;
import seedu.geekeep.model.UserPrefs;

/**
 * Manages storage of GeeKeep data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private GeeKeepStorage geeKeepStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(GeeKeepStorage geeKeepStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.geeKeepStorage = geeKeepStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String geeKeepFilePath, String userPrefsFilePath) {
        this(new XmlGeeKeepStorage(geeKeepFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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


    // ================ GeeKeep methods ==============================

    @Override
    public String getGeeKeepFilePath() {
        return geeKeepStorage.getGeeKeepFilePath();
    }

    @Override
    public Optional<ReadOnlyGeeKeep> readGeeKeep() throws DataConversionException, IOException {
        return readGeeKeep(geeKeepStorage.getGeeKeepFilePath());
    }

    @Override
    public Optional<ReadOnlyGeeKeep> readGeeKeep(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return geeKeepStorage.readGeeKeep(filePath);
    }

    @Override
    public void saveGeeKeep(ReadOnlyGeeKeep geeKeep) throws IOException {
        saveGeeKeep(geeKeep, geeKeepStorage.getGeeKeepFilePath());
    }

    @Override
    public void saveGeeKeep(ReadOnlyGeeKeep geeKeep, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        geeKeepStorage.saveGeeKeep(geeKeep, filePath);
    }


    @Override
    @Subscribe
    public void handleGeeKeepChangedEvent(GeeKeepChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveGeeKeep(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
