package seedu.watodo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.watodo.commons.events.model.TaskListChangedEvent;
import seedu.watodo.commons.events.storage.DataSavingExceptionEvent;
import seedu.watodo.commons.exceptions.DataConversionException;
import seedu.watodo.model.ReadOnlyTaskManger;
import seedu.watodo.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskListFilePath();

    @Override
    Optional<ReadOnlyTaskManger> readTaskList() throws DataConversionException, IOException;

    @Override
    void saveTaskList(ReadOnlyTaskManger TaskList) throws IOException;

    /**
     * Saves the current version of the Task List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskListChangedEvent(TaskListChangedEvent abce);
}
