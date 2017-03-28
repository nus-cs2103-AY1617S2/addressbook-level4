package seedu.doist.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.doist.commons.exceptions.DataConversionException;
import seedu.doist.model.ReadOnlyAliasListMap;

//@@author A0140887W-reused
/**
 * Represents a storage for {@link seedu.doist.model.AliasListMap}.
 */
public interface AliasListMapStorage {

    /**
     * Returns AliasListMap data as a {@link ReadOnlyAliasListMap}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAliasListMap> readAliasListMap() throws DataConversionException, IOException;

    /**
     * @see #readAliasListMap()
     * @see #getAliasListFilePath()
     */
    Optional<ReadOnlyAliasListMap> readAliasListMap(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAliasListMap} to the storage.
     * @param aliasList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAliasListMap(ReadOnlyAliasListMap aliasList) throws IOException;

    /**
     * @see #saveAliasListMap(ReadOnlyAliasListMap)
     */
    void saveAliasListMap(ReadOnlyAliasListMap aliasList, String filePath) throws IOException;

    /**
     * Returns the file path of the data file.
     */
    String getAliasListMapFilePath();

    /**
     * Sets the file path of the data file.
     */
    void setAliasListMapFilePath(String path);

}
