package seedu.taskmanager.model;

import javafx.collections.ObservableList;
import seedu.taskmanager.model.category.Category;
import seedu.taskmanager.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the tasks list. This list will not
     * contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the categories list. This list will not contain
     * any duplicate categories.
     */
    ObservableList<Category> getCategoryList();

}
