package werkbook.task.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import werkbook.task.commons.events.model.TaskListChangedEvent;
import werkbook.task.commons.events.storage.DataSavingExceptionEvent;
import werkbook.task.commons.exceptions.DataConversionException;
import werkbook.task.model.ReadOnlyTaskList;
import werkbook.task.model.UserPrefs;

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
    Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException;

    @Override
    void saveTaskList(ReadOnlyTaskList taskList) throws IOException;

    /**
     * Saves the current version of the Task List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskListChangedEvent(TaskListChangedEvent abce);

    void setTaskListFilePath(Path filePath) throws IOException;
}
