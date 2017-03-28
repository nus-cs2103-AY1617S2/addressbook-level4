package seedu.taskboss.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.taskboss.commons.events.model.TaskBossChangedEvent;
import seedu.taskboss.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskboss.commons.exceptions.DataConversionException;
import seedu.taskboss.model.ReadOnlyTaskBoss;
import seedu.taskboss.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskBossStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskBossFilePath();

    @Override
    Optional<ReadOnlyTaskBoss> readTaskBoss() throws DataConversionException, IOException;

    //@@author A0138961W
    @Override
    void saveTaskBoss(ReadOnlyTaskBoss taskBoss) throws IOException;

    /**
     * Saves the current version of TaskBoss to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskBossChangedEvent(TaskBossChangedEvent tbce);

    void setFilePath(String filepath) throws IOException;
}
