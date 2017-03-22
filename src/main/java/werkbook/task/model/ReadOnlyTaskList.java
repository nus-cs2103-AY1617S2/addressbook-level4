package werkbook.task.model;


import javafx.collections.ObservableList;
import werkbook.task.model.tag.Tag;
import werkbook.task.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an task list
 */
public interface ReadOnlyTaskList {

    /**
     * Returns an unmodifiable view of the task list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
