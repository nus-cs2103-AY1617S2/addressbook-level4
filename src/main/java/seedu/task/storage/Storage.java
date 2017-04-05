package seedu.task.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.task.commons.events.model.TaskListChangedEvent;
import seedu.task.commons.events.storage.DataSavingExceptionEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskList;
import seedu.task.model.UserPrefs;

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

    //@@author A0163559U
    /**
     * Attempts to save current version of the Task Manager to new file location
     * @param taskList
     * @param saveFile
     * @throws IOException
     */
    @Override
    void saveTaskListInNewLocation(ReadOnlyTaskList taskList, File saveFile) throws IOException;

    /**
     * Attempts to load Task Manager data from specified file location
     * @param taskList
     * @param loadFile
     * @return
     * @throws DataConversionException
     * @throws FileNotFoundException
     */
    @Override
    Optional<ReadOnlyTaskList> loadTaskListFromNewLocation(ReadOnlyTaskList taskList, File loadFile)
            throws FileNotFoundException, DataConversionException;

    //@@author
}
