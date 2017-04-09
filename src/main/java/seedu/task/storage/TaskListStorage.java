package seedu.task.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskList;

/**
 * Represents a storage for {@link seedu.task.model.TaskList}.
 */
public interface TaskListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskListFilePath();

    /**
     * Returns TaskList data as a {@link ReadOnlyTaskList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException;

    /**
     * @see #getTaskListFilePath()
     */
    Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskList} to the storage.
     * @param TaskList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(ReadOnlyTaskList taskList) throws IOException;

    /**
     * @see #saveTaskList(ReadOnlyTaskList)
     */
    void saveTaskList(ReadOnlyTaskList taskList, String filePath) throws IOException;

    //@@author A0163559U
    /**
     * Copies the current save state of the Task Manager into a new location
     * @param taskList is the state to be copied
     * @param newFile is the new save location
     * @throws IOException if file operations fail
     */
    void saveTaskListInNewLocation(ReadOnlyTaskList taskList, File newFile) throws IOException;

    /**
     * Loads saved state of Task Manager from specified location
     * @param loadFile is the file to load state from
     * @return new loaded task list, if successful
     * @throws DataConversionException if data conversion fails
     * @throws FileNotFoundException if file operation fails
     */
    Optional<ReadOnlyTaskList> loadTaskListFromNewLocation(File loadFile)
            throws FileNotFoundException, DataConversionException;
    //@@author

}
