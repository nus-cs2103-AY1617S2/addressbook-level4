package seedu.opus.sync;

import java.io.IOException;
import java.util.List;

import seedu.opus.model.task.Task;
import seedu.opus.sync.exceptions.SyncException;

public interface syncService {

    /**
     * Initialise and start the service
     * @throws SyncException
     * @throws IOException
     */
    public void start() throws IOException, SyncException;

    /**
     * Stop service
     */
    public void stop();

    /**
     * Add task to be inserted into service's task list
     * @param taskToAdd
     */
    public void addTask(Task taskToAdd);

    /**
     * Add task to be deleted from service's task list
     * @param taskToDelete
     */
    public void deleteTask(Task taskToDelete);

    /**
     * Add task to be updated in service's task list
     * @param taskToUpdate
     */
    public void updateTask(Task taskToUpdate);

    /**
     * Pull the latest copy of task list from service
     * @return list of task
     */
    public List<Task> pullTaskList();

    public void updateTaskList(List<Task> taskList);
}
