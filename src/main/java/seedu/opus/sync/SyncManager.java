package seedu.opus.sync;

import java.util.List;

import seedu.opus.commons.core.ComponentManager;
import seedu.opus.commons.events.ui.NewResultAvailableEvent;
import seedu.opus.model.task.Task;
import seedu.opus.sync.exceptions.SyncException;

//@@author A0148087W
/**
 * Manage all available sync services and push/pull requests from Model
 */
public class SyncManager extends ComponentManager implements Sync {

    private SyncService service;

    public SyncManager(SyncService service) {
        this.service = service;
        this.service.setSyncManager(this);
    }

    @Override
    public void updateTaskList(List<Task> taskList) {
        service.updateTaskList(taskList);
    }

    @Override
    public void startSync() throws SyncException {
        this.service.start();
    }

    @Override
    public void stopSync() {
        this.service.stop();
    }

    @Override
    public void raiseSyncEvent(SyncException exception) {
        raise(new NewResultAvailableEvent(exception.getMessage()));
    }
}
