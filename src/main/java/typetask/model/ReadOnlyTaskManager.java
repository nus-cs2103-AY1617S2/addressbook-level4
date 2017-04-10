package typetask.model;


import javafx.collections.ObservableList;
import typetask.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of a TaskManager
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the tasks list.
     */
    ObservableList<ReadOnlyTask> getTaskList();

}
