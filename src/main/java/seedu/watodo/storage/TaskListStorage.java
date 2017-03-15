package seedu.watodo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.watodo.commons.exceptions.DataConversionException;
import seedu.watodo.model.ReadOnlyTaskManger;

/**
 * Represents a storage for {@link seedu.watodo.model.TaskManager}.
 */
public interface TaskListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskListFilePath();

    /**
     * Returns TaskManager data as a {@link ReadOnlyTaskManger}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskManger> readTaskList() throws DataConversionException, IOException;

    /**
     * @see #getTaskListFilePath()
     */
    Optional<ReadOnlyTaskManger> readTaskList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskManger} to the storage.
     * @param TaskManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(ReadOnlyTaskManger TaskList) throws IOException;

    /**
     * @see #saveTaskList(ReadOnlyTaskManger)
     */
    void saveTaskList(ReadOnlyTaskManger TaskList, String filePath) throws IOException;

}
