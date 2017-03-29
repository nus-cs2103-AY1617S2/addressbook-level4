package seedu.doit.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.doit.commons.events.model.TaskManagerChangedEvent;
import seedu.doit.commons.events.storage.DataSavingExceptionEvent;
import seedu.doit.commons.events.storage.TaskManagerLoadChangedEvent;
import seedu.doit.commons.events.storage.TaskManagerSaveChangedEvent;
import seedu.doit.commons.exceptions.DataConversionException;
import seedu.doit.model.ReadOnlyItemManager;
import seedu.doit.model.UserPrefs;

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
    Optional<ReadOnlyItemManager> readTaskManager() throws DataConversionException, IOException;

    @Override
    void saveTaskManager(ReadOnlyItemManager taskManager) throws IOException;

    /**
     * Saves the current version of the Task Manager to the hard disk. Creates
     * the data file if it is missing. Raises {@link DataSavingExceptionEvent}
     * if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);

    // @@author A0138909R
    /**
     * Creates a new file path for the Task Manager to save. Saves the current
     * version of the Task Manager to the hard disk at the new location. Creates
     * the data file if it is missing. Raises {@link DataSavingExceptionEvent}
     * if there was an error during saving.
     */
    void handleTaskManagerSaveChangedEvent(TaskManagerSaveChangedEvent event);

    /**
     * Loads an existing file path for the Task Manager.
     */
    void handleTaskManagerLoadChangedEvent(TaskManagerLoadChangedEvent event);
    // @@author
}
