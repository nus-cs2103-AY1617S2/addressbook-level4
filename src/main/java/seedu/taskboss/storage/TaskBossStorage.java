package seedu.taskboss.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.taskboss.commons.exceptions.DataConversionException;
import seedu.taskboss.model.ReadOnlyTaskBoss;

/**
 * Represents a storage for {@link seedu.taskboss.model.TaskBoss}.
 */
public interface TaskBossStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskBossFilePath();

    /**
     * Returns TaskBoss data as a {@link ReadOnlyTaskBoss}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskBoss> readTaskBoss() throws DataConversionException, IOException;

    /**
     * @see #getTaskBossFilePath()
     */
    Optional<ReadOnlyTaskBoss> readTaskBoss(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskBoss} to the storage.
     * @param taskBoss cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskBoss(ReadOnlyTaskBoss taskBoss) throws IOException;

    /**
     * @see #saveTaskBoss(ReadOnlyTaskBoss)
     */
    void saveTaskBoss(ReadOnlyTaskBoss taskBoss, String filePath) throws IOException;

}
