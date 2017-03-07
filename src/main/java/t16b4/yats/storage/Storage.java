package t16b4.yats.storage;

import java.io.IOException;
import java.util.Optional;

import t16b4.yats.commons.events.model.AddressBookChangedEvent;
import t16b4.yats.commons.events.storage.DataSavingExceptionEvent;
import t16b4.yats.commons.exceptions.DataConversionException;
import t16b4.yats.model.ReadOnlyTaskManager;
import t16b4.yats.model.UserPrefs;

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
    Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyTaskManager addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);
}
