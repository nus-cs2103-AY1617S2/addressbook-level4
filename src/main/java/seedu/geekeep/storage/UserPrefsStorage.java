package seedu.geekeep.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.model.UserPrefs;

/**
 * Represents a storage for {@link seedu.geekeep.model.UserPrefs}.
 */
public interface UserPrefsStorage {

    /**
     * Returns UserPrefs data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     */
    Optional<UserPrefs> readUserPrefs() throws DataConversionException;

    /**
     * Saves the given {@link seedu.geekeep.model.UserPrefs} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

}
