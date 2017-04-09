package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.ui.ExportRequestEvent;
import seedu.address.commons.events.ui.ImportRequestEvent;
import seedu.address.commons.events.ui.LoadRequestEvent;
import seedu.address.commons.events.ui.TargetFileRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskManagerStorage, UserPrefsStorage {

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
    void handleAddressBookChangedEvent(TaskManagerChangedEvent abce);

    //@@author A0163848R
    /**
     * Saves the current version of the Address Book to the hard disk at a specified path.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleExportRequestEvent(ExportRequestEvent ere);

    /**
     * Retrieves an Address Book at the specified path.
     */
    void handleImportRequestEvent(ImportRequestEvent ire);

    /**
     * Sets the file to which Address Book saving is done.
     */
    void handleTargetFileRequestEvent(TargetFileRequestEvent tfre);

    void handleLoadRequestEvent(LoadRequestEvent ire);

}
