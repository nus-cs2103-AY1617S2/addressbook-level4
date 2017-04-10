package onlythree.imanager.storage;

import java.io.IOException;
import java.util.Optional;

import onlythree.imanager.commons.events.model.TaskListChangedEvent;
import onlythree.imanager.commons.events.storage.DataSavingExceptionEvent;
import onlythree.imanager.commons.events.storage.FileLocationChangedEvent;
import onlythree.imanager.commons.exceptions.DataConversionException;
import onlythree.imanager.model.ReadOnlyTaskList;
import onlythree.imanager.model.UserPrefs;

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

    void handleFileLocationChangedEvent(FileLocationChangedEvent event) throws DataConversionException;
}
