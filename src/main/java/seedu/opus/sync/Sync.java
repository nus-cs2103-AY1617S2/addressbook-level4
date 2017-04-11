package seedu.opus.sync;

import java.io.IOException;
import java.util.List;

import seedu.opus.model.task.Task;
import seedu.opus.sync.exceptions.SyncException;

//@@author A0148087W
/**
 * The API for sync component
 */
public interface Sync {

    public void startSync() throws IOException, SyncException;

    public void stopSync();

    /**
     * Pushes Current Tasks in Model to sync service
     * @param taskList
     */
    public void updateTaskList(List<Task> taskList);

    /**
     * Raise any exception encountered in syncSerice to UI Result Display
     * @param exception
     */
    public void raiseSyncEvent(SyncException exception);
}
