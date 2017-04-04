package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;

//@@author A0121668A
/**
 * Represents a storage for {@link seedu.address.commons.core.Config}.
 */
public interface UserConfigStorage {

    /**
     * Returns Configuration data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Config> readUserConfig() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.commons.core.Config} to the storage.
     * @param config cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserConfig(Config config) throws IOException;

}
