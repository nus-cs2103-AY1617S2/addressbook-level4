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
     * Pushes Current Task in Model to sync service
     * @param taskList
     */
    public void updateTaskList(List<Task> taskList);
}
