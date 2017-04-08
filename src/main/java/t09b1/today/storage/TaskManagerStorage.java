package t09b1.today.storage;

import java.io.IOException;
import java.util.Optional;

import t09b1.today.commons.exceptions.DataConversionException;
import t09b1.today.model.ReadOnlyTaskManager;

/**
 * Represents a storage for {@link t09b1.today.model.TaskManager}.
 */
public interface TaskManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskManagerFilePath();

    /**
     * Returns TaskManager data as a {@link ReadOnlyTaskManager}. Returns
     * {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException
     *             if the data in storage is not in the expected format.
     * @throws IOException
     *             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    /**
     * @see #getTaskManagerFilePath()
     */
    Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskManager} to the storage.
     *
     * @param taskManager
     *            cannot be null.
     * @throws IOException
     *             if there was any problem writing to the file.
     */
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;

    /**
     * @see #saveTaskManager(ReadOnlyTaskManager)
     */
    void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException;

    /*
     * Set TaskManager's save location to specified filePath
     *
     * @param filePath must be a valid absolute file path
     */
    void setTaskManagerFilePath(String filePath);

}
