package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.WhatsLeftChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends WhatsLeftStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getWhatsLeftFilePath();

    @Override
    Optional<ReadOnlyWhatsLeft> readWhatsLeft() throws DataConversionException, IOException;

    @Override
    void saveWhatsLeft(ReadOnlyWhatsLeft whatsLeft) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleWhatsLeftChangedEvent(WhatsLeftChangedEvent abce);
}
