package seedu.opus.sync;

import java.io.IOException;
import java.util.List;

import seedu.opus.model.task.Task;
import seedu.opus.sync.exceptions.SyncException;

/**
 * An interface representing a sync service provider
 *
 */
public interface SyncService {

    /**
     * Initialise and start the service
     * @throws SyncException
     * @throws IOException
     */
    public void start();

    /**
     * Stop service
     */
    public void stop();

    /**
     * Updates task list in sync service with provided taskList
     * @param taskList
     */
    public void updateTaskList(List<Task> taskList);
}
