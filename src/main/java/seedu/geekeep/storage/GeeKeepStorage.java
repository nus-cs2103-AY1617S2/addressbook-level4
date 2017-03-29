package seedu.geekeep.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.model.ReadOnlyGeeKeep;

/**
 * Represents a storage for {@link seedu.geekeep.model.GeeKeep}.
 */
public interface GeeKeepStorage {

    /**
     * Returns the file path of the data file.
     */
    String getGeeKeepFilePath();

    /**
     * Set the file path of the data file.
     */
    void setGeeKeepFilePath(String filePath);

    /**
     * Returns GeeKeep data as a {@link ReadOnlyGeeKeep}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGeeKeep> readGeeKeep() throws DataConversionException, IOException;

    /**
     * @see #getGeeKeepFilePath()
     */
    Optional<ReadOnlyGeeKeep> readGeeKeep(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGeeKeep} to the storage.
     * @param geeKeep cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGeeKeep(ReadOnlyGeeKeep geeKeep) throws IOException;

    /**
     * @see #saveGeeKeep(ReadOnlyGeeKeep)
     */
    void saveGeeKeep(ReadOnlyGeeKeep geeKeep, String filePath) throws IOException;

}
