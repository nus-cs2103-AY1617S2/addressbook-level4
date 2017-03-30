package seedu.bulletjournal.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.bulletjournal.commons.events.model.TodoListChangedEvent;
import seedu.bulletjournal.commons.events.storage.DataSavingExceptionEvent;
import seedu.bulletjournal.commons.exceptions.DataConversionException;
import seedu.bulletjournal.model.ReadOnlyTodoList;
import seedu.bulletjournal.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TodoListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getBulletJournalFilePath();

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
