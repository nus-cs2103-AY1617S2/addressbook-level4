package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyToDoApp;

/**
 * Represents a storage for {@link seedu.address.model.ToDoApp}.
 */
public interface ToDoAppStorage {

    /**
     * Returns the file path of the data file.
     */
    String getToDoAppFilePath();

    /**
     * Returns ToDoApp data as a {@link ReadOnlyToDoApp}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyToDoApp> readToDoApp() throws DataConversionException, IOException;

    /**
     * @see #getToDoAppFilePath()
     */
    Optional<ReadOnlyToDoApp> readToDoApp(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyToDoApp} to the storage.
     * @param ToDoApp cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveToDoApp(ReadOnlyToDoApp toDoApp) throws IOException;

    /**
     * @see #saveToDoApp(ReadOnlyToDoApp)
     */
    void saveToDoApp(ReadOnlyToDoApp toDoApp, String filePath) throws IOException;

}
