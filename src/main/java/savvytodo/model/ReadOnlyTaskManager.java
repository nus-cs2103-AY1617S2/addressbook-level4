package savvytodo.model;

import javafx.collections.ObservableList;
import savvytodo.model.category.Category;
import savvytodo.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the category list.
     * This list will not contain any duplicate categories.
     */
    ObservableList<Category> getCategoryList();

}
