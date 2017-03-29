package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.model.TaskManagerExportEvent;
import seedu.address.commons.events.model.TaskManagerPathChangedEvent;
import seedu.address.commons.events.model.TaskManagerUseNewPathEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
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
    String getTaskManagerFilePath();

    @Override
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    /** Returns the initial data read from file **/
    public ReadOnlyTaskManager getInitialData();

    @Override
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk. Creates
     * the data file if it is missing. Raises {@link DataSavingExceptionEvent}
     * if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);

    /**
     * Updates the storage file path.
     */
    void handleTaskManagerPathChangedEvent(TaskManagerPathChangedEvent event);

    /**
     * Read from new storage file path.
     */
    void handleTaskManagerUseNewPathEvent(TaskManagerUseNewPathEvent event);

    /**
     * Export to specified file path.
     */
    void handleTaskManagerExportEvent(TaskManagerExportEvent event);
}
