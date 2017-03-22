package seedu.tache.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.tache.commons.events.model.TaskManagerChangedEvent;
import seedu.tache.commons.events.storage.DataSavingExceptionEvent;
import seedu.tache.commons.exceptions.DataConversionException;
import seedu.tache.model.ReadOnlyTaskManager;
import seedu.tache.model.UserPrefs;

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
    void setTaskManagerFilePath(String newPath);

    @Override
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    @Override
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;

    /**
     * Saves the current version of the Task Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);

}
