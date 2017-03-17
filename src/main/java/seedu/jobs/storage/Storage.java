package seedu.jobs.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.jobs.commons.events.model.AddressBookChangedEvent;
import seedu.jobs.commons.events.storage.DataSavingExceptionEvent;
import seedu.jobs.commons.exceptions.DataConversionException;
import seedu.jobs.model.ReadOnlyTaskBook;
import seedu.jobs.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyTaskBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyTaskBook addressBook) throws IOException;

    /**
     * Saves the current version of the Description Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);
}
