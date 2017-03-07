package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

<<<<<<< HEAD
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.UserPrefs;
=======
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.storage.DataSavingExceptionEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.UserPrefs;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285

/**
 * API of the Storage component
 */
public interface Storage extends TaskManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskManagerFilePath();

    @Override
<<<<<<< HEAD
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    @Override
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;
=======
    Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyTaskManager addressBook) throws IOException;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
<<<<<<< HEAD
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);
=======
    void handleAddressBookChangedEvent(TaskManagerChangedEvent abce);
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285
}
