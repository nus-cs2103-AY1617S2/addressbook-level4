package seedu.ezdo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.ezdo.commons.exceptions.DataConversionException;
import seedu.ezdo.model.ReadOnlyEzDo;

/**
 * Represents a storage for {@link seedu.ezdo.model.EzDo}.
 */
public interface EzDoStorage {

    /**
     * Returns the file path of the data file.
     */
    String getEzDoFilePath();

    /**
     * Returns EzDo data as a {@link ReadOnlyEzDo}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEzDo> readEzDo() throws DataConversionException, IOException;

    /**
     * @see #getEzDoFilePath()
     */
    Optional<ReadOnlyEzDo> readEzDo(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEzDo} to the storage.
     * @param ezDo cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEzDo(ReadOnlyEzDo ezDo) throws IOException;

    /**
     * @see #saveEzDo(ReadOnlyEzDo)
     */
    void saveEzDo(ReadOnlyEzDo ezDo, String filePath) throws IOException;

}
