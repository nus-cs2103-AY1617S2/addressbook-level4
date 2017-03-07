package t16b4.yats.storage;

import java.io.IOException;
import java.util.Optional;

import t16b4.yats.commons.exceptions.DataConversionException;
import t16b4.yats.model.ReadOnlyTaskManager;

/**
 * Represents a storage for {@link t16b4.yats.model.TaskManager}.
 */
public interface TaskManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskManagerFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyTaskManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    /**
     * @see #getTaskManagerFilePath()
     */
    Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskManager} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskManager(ReadOnlyTaskManager addressBook) throws IOException;

    /**
     * @see #saveTaskManager(ReadOnlyTaskManager)
     */
    void saveTaskManager(ReadOnlyTaskManager addressBook, String filePath) throws IOException;

}
