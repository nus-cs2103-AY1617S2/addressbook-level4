package seedu.opus.sync;

import java.util.List;

import seedu.opus.model.task.Task;

//@@author A0148087W
/**
 * Manage all available sync services and push/pull requests from Model
 *
 */
public class SyncManager implements Sync {

    private SyncService service;

    public SyncManager(SyncService service) {
        this.service = service;
    }

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
