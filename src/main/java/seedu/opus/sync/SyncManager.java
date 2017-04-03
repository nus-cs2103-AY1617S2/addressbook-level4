package seedu.opus.sync;

import java.util.List;

import seedu.opus.model.task.Task;

//@@author A0148087W
/**
 * Manage all available sync services and push/pull requests from Model
 *
 */
public class SyncManager implements Sync {

    public SyncManager(SyncService service) {
        this.service = service;
    }

    private SyncService service;

    @Override
    public void updateTaskList(List<Task> taskList) {
        service.updateTaskList(taskList);
    }

    @Override
    public void startSync() {
        this.service.start();
    }

    @Override
    public void stopSync() {
        this.service.stop();
    }

}
