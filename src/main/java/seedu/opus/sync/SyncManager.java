package seedu.opus.sync;

import java.io.IOException;
import java.util.List;

import seedu.opus.model.task.Task;
import seedu.opus.sync.exceptions.SyncException;

public class SyncManager implements Sync {

    public SyncManager(syncService service) {
        this.service = service;
    }

    private syncService service;

    @Override
    public void addTask(Task taskToAdd) {
        service.addTask(taskToAdd);
    }

    @Override
    public void deleteTask(Task taskToDelete) {
        service.deleteTask(taskToDelete);
    }

    @Override
    public void updateTask(Task taskToUpdate) {
        service.updateTask(taskToUpdate);
    }

    @Override
    public List<Task> getTaskListFromSync() {
        return null;
    }

    @Override
    public void updateTaskList(List<Task> taskList) {
        service.updateTaskList(taskList);
    }

    @Override
    public void startSync() throws IOException, SyncException {
        this.service.start();
    }

    @Override
    public void stopSync() {
        this.service.stop();
    }

}
