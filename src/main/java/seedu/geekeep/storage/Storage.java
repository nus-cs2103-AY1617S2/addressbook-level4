package seedu.geekeep.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.geekeep.commons.events.model.GeeKeepChangedEvent;
import seedu.geekeep.commons.events.storage.DataSavingExceptionEvent;
import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.model.ReadOnlyGeeKeep;
import seedu.geekeep.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends GeeKeepStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getGeeKeepFilePath();

    @Override
    Optional<ReadOnlyGeeKeep> readGeeKeep() throws DataConversionException, IOException;

    @Override
    void saveGeeKeep(ReadOnlyGeeKeep geeKeep) throws IOException;

    /**
     * Saves the current version of the Task Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleGeeKeepChangedEvent(GeeKeepChangedEvent abce);
}
