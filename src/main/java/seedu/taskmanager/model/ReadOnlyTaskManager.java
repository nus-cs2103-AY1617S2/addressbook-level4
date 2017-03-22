package seedu.taskmanager.model;


import javafx.collections.ObservableList;
//import seedu.taskmanager.model.category.Category;
import seedu.taskmanager.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
//    ObservableList<Category> getCategoryList();

}
