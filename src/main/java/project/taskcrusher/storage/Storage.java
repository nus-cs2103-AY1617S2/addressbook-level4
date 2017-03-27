package project.taskcrusher.storage;

import java.io.IOException;
import java.util.Optional;

import project.taskcrusher.commons.events.model.AddressBookChangedEvent;
import project.taskcrusher.commons.events.storage.DataSavingExceptionEvent;
import project.taskcrusher.commons.exceptions.DataConversionException;
import project.taskcrusher.model.ReadOnlyUserInbox;
import project.taskcrusher.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends UserInboxStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getUserInboxFilePath();

    @Override
    Optional<ReadOnlyUserInbox> readUserInbox() throws DataConversionException, IOException;

    @Override
    void saveUserInbox(ReadOnlyUserInbox addressBook) throws IOException;

    void loadNewStorageWith(String storageFilename);
    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);
}
