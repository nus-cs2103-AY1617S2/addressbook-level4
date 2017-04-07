package seedu.opus.sync;

import java.io.IOException;
import java.util.List;

import seedu.opus.model.task.Task;
import seedu.opus.sync.exceptions.SyncException;

//@@author A0148087W
/**
 * An interface representing a sync service provider
 *
 */
public abstract class SyncService {

    protected Sync syncManager;

    /**
     * Initialise and start the service
     * @throws SyncException
     * @throws IOException
     */
    public abstract void start() throws SyncException;

    /**
     * Stop service
     */
    public abstract void stop();

    /**
     * Updates task list in sync service with provided taskList
     * @param taskList
     */
    public abstract void updateTaskList(List<Task> taskList);

    public void setSyncManager(Sync syncManager) {
        this.syncManager = syncManager;
    }
}
