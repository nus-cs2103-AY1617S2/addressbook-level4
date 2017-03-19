package seedu.doit.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.doit.commons.exceptions.DataConversionException;
import seedu.doit.model.ReadOnlyItemManager;
import seedu.doit.model.TaskManager;

/**
 * Represents a storage for {@link TaskManager}.
 */
public interface TaskManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskManagerFilePath();

    /**
     * Changes the file path of the data file.
     */
    void setTaskManagerFilePath(String filePath);

    /**
     * Returns TaskManager data as a {@link ReadOnlyItemManager}. Returns
     * {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException
     *             if the data in storage is not in the expected format.
     * @throws IOException
     *             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyItemManager> readTaskManager() throws DataConversionException, IOException;

    /**
     * @see #getTaskManagerFilePath()
     */
    Optional<ReadOnlyItemManager> readTaskManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyItemManager} to the storage.
     *
     * @param taskManager
     *            cannot be null.
     * @throws IOException
     *             if there was any problem writing to the file.
     */
    void saveTaskManager(ReadOnlyItemManager taskManager) throws IOException;

    /**
     * @see #saveTaskManager(ReadOnlyItemManager)
     */
    void saveTaskManager(ReadOnlyItemManager taskManager, String filePath) throws IOException;

    void copyTaskManager(String oldPath, String newPath) throws IOException;

}
