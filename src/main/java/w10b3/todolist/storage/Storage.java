package w10b3.todolist.storage;

import java.io.IOException;
import java.util.Optional;

import w10b3.todolist.commons.events.model.ToDoListChangedEvent;
import w10b3.todolist.commons.events.storage.DataSavingExceptionEvent;
import w10b3.todolist.commons.exceptions.DataConversionException;
import w10b3.todolist.model.ReadOnlyToDoList;
import w10b3.todolist.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ToDoListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getToDoListFilePath();

    @Override
    Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException;

    @Override
    void saveToDoList(ReadOnlyToDoList toDoList) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleToDoListChangedEvent(ToDoListChangedEvent abce);
}
