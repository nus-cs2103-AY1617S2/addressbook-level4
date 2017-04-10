package seedu.task.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.storage.DataSavingExceptionEvent;
import seedu.task.commons.events.storage.ExportRequestEvent;
import seedu.task.commons.events.storage.ImportRequestEvent;
import seedu.task.commons.events.storage.LoadRequestEvent;
import seedu.task.commons.events.storage.TargetFileRequestEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.UserPrefs;

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

    @Override
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;

    /**
     * Saves the current version of the Task Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);

    //@@author A0163848R
    /**
     * Saves the current version of the Task Manager to the hard disk at a specified path.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleExportRequestEvent(ExportRequestEvent ere);

    /**
     * Retrieves an Task Manager at the specified path.
     */
    void handleImportRequestEvent(ImportRequestEvent ire);

    /**
     * Sets the file to which Task Manager saving is done.
     */
    void handleTargetFileRequestEvent(TargetFileRequestEvent tfre);

    void handleLoadRequestEvent(LoadRequestEvent ire);
    //@@author
}
