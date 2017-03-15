package seedu.taskboss.model;


import javafx.collections.ObservableList;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of TaskBoss
 */
public interface ReadOnlyTaskBoss {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the categories list.
     * This list will not contain any duplicate categories.
     */
    ObservableList<Category> getCategoryList();

}
