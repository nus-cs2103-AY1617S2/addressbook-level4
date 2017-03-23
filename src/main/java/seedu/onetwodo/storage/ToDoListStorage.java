package seedu.onetwodo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.onetwodo.commons.exceptions.DataConversionException;
import seedu.onetwodo.model.ReadOnlyToDoList;

/**
 * Represents a storage for {@link seedu.onetwodo.model.ToDoList}.
 */
public interface ToDoListStorage {
    
    /**
     * Returns the file path of the data file.
     */
    String getToDoListFilePath();

    /**
     * Set a new file path
     */
    void setToDoListFilePath(String newFilePath);
    
    /**
     * Returns ToDoList data as a {@link ReadOnlyToDoList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException;

    /**
     * @see #getToDoListFilePath()
     */
    Optional<ReadOnlyToDoList> readToDoList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyToDoList} to the storage.
     * @param toDoList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveToDoList(ReadOnlyToDoList toDoList) throws IOException;

    /**
     * @see #saveToDoList(ReadOnlyToDoList)
     */
    void saveToDoList(ReadOnlyToDoList toDoList, String filePath) throws IOException;

}
