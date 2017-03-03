package seedu.doist.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.doist.commons.events.model.TodoListChangedEvent;
import seedu.doist.commons.events.storage.DataSavingExceptionEvent;
import seedu.doist.commons.exceptions.DataConversionException;
import seedu.doist.model.ReadOnlyTodoList;
import seedu.doist.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TodoListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyTodoList> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyTodoList addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(TodoListChangedEvent abce);
}
