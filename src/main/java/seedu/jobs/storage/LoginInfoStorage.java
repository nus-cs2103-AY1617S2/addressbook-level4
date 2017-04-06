package seedu.jobs.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.jobs.commons.exceptions.DataConversionException;
import seedu.jobs.model.LoginInfo;

public interface LoginInfoStorage {
    /**
     * Returns LoginInfo data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<LoginInfo> readLoginInfo() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.jobs.model.LoginInfo} to the storage.
     * @param loginInfo cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */

    void saveLoginInfo(LoginInfo loginInfo) throws IOException;
}
