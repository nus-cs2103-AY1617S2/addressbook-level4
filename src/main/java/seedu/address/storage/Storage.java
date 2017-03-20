package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;


import seedu.address.commons.events.model.TodoListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.SaveFilePathChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTodoList;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TodoListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTodoListFilePath();

    @Override
    Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException;

    @Override
    void saveTodoList(ReadOnlyTodoList todoList) throws IOException;

    /**
     * Saves the current version of the Todo List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTodoListChangedEvent(TodoListChangedEvent abce);

    /**
     * Updates the save file location .
     */
    void handleSaveFilePathChangedEvent(SaveFilePathChangedEvent evt);
}
