package seedu.geekeep.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.model.Config;

/**
 * Represents a storage for {@link seedu.geekeep.model.Config}.
 */
public interface ConfigStorage {

    /**
     * Returns Config data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     */
    Optional<Config> readConfig() throws DataConversionException;

    /**
     * Saves the given {@link seedu.geekeep.model.Config} to the storage.
     * @param config cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveConfig(Config config) throws IOException;

}
