//@@author A0139248X
package seedu.ezdo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.events.storage.DataSavingExceptionEvent;
import seedu.ezdo.commons.events.storage.EzDoDirectoryChangedEvent;
import seedu.ezdo.commons.exceptions.DataConversionException;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends EzDoStorage, UserPrefsStorage {
    /**
     * Read the UserPrefs data from Storage
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.ezdo.model.UserPrefs} to the storage.
     *
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    /** Returns the file path of EzDo */
    @Override
    String getEzDoFilePath();

    /**
     * Sets the file path of EzDo
     *
     * @param path cannot be null.
     */
    @Override
    void setEzDoFilePath(String path);

    /**
     * Returns EzDo data as a {@link ReadOnlyEzDo}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    @Override
    Optional<ReadOnlyEzDo> readEzDo() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEzDo} to the storage.
     *
     * @param ezDo cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    @Override
    void saveEzDo(ReadOnlyEzDo ezDo) throws IOException;

    /**
     * Saves the current version of the EzDo to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleEzDoChangedEvent(EzDoChangedEvent ezce);

    /**
     * Changes the current directory of the ezDo
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleEzDoDirectoryChangedEvent(EzDoDirectoryChangedEvent ezdce);
}
