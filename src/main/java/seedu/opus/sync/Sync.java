package seedu.opus.sync;

import java.util.List;

import seedu.opus.model.task.Task;

//@@author A0148087W
/**
 * The API for sync component
 */
public interface Sync {

    public void startSync();

    public void stopSync();

    /**
     * Add a task to be pushed to task list in sync server
     * @param taskToAdd
     */
    public void addTask(Task taskToAdd);

    /**
     * Add a task to be deleted from task list in sync server
     * @param taskToDelete
     */
    public void deleteTask(Task taskToDelete);

    /**
     * Add a task to be updated in the task list in sync server
     * @param taskToUpdate
     */
    public void updateTask(Task taskToUpdate);

    /**
     * Pulls all tasks in sync server
     * @return
     */
    public List<Task> getTaskListFromSync();

    public void updateTaskList(List<Task> taskList);
}
