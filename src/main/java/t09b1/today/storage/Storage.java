package t09b1.today.storage;

import java.io.IOException;
import java.util.Optional;

import t09b1.today.commons.events.model.TaskManagerChangedEvent;
import t09b1.today.commons.events.model.TaskManagerExportEvent;
import t09b1.today.commons.events.model.TaskManagerImportEvent;
import t09b1.today.commons.events.model.TaskManagerPathChangedEvent;
import t09b1.today.commons.events.model.TaskManagerUseNewPathEvent;
import t09b1.today.commons.events.storage.DataSavingExceptionEvent;
import t09b1.today.commons.exceptions.DataConversionException;
import t09b1.today.model.ReadOnlyTaskManager;
import t09b1.today.model.UserPrefs;

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
    public ReadOnlyTaskManager getInitialData(String path);

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

    /**
     * Import from specified file path.
     */
    void handleTaskManagerImportEvent(TaskManagerImportEvent event);
}
