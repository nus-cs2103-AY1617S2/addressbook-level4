package savvytodo.storage;

import java.io.IOException;
import java.util.Optional;

import savvytodo.commons.exceptions.DataConversionException;
import savvytodo.model.ReadOnlyTaskManager;

/**
 * Represents a storage for {@link savvytodo.model.TaskManager}.
 */
public interface TaskManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String gettaskManagerFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyTaskManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskManager> readTaskManaager() throws DataConversionException, IOException;

    /**
     * @see #gettaskManagerFilePath()
     */
    Optional<ReadOnlyTaskManager> taskManagerBook(String filePath) throws DataConversionException, IOException;

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
