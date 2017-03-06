package seedu.taskList.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.taskList.commons.events.model.TaskListChangedEvent;
import seedu.taskList.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskList.commons.exceptions.DataConversionException;
import seedu.taskList.model.ReadOnlyTaskList;
import seedu.taskList.model.UserPrefs;

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
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskListChangedEvent(TaskListChangedEvent abce);
}
