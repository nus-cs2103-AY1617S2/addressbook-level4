package seedu.geekeep.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.geekeep.commons.events.model.GeeKeepChangedEvent;
import seedu.geekeep.commons.events.model.GeekeepFilePathChangedEvent;
import seedu.geekeep.commons.events.storage.DataSavingExceptionEvent;
import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.model.Config;
import seedu.geekeep.model.ReadOnlyGeeKeep;
import seedu.geekeep.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ConfigStorage, GeeKeepStorage, UserPrefsStorage {

    //@@author A0147622H
    @Override
    Optional<Config> readConfig() throws DataConversionException;

    @Override
    void saveConfig(Config config) throws IOException;

    //@@author
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getGeeKeepFilePath();

    //@@author A0147622H
    @Override
    void setGeeKeepFilePath(String filePath);

    //@@author
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

    //@@author A0147622H
    void handleGeekeepFilePathChangedEvent(GeekeepFilePathChangedEvent event) throws IOException;
}
