package onlythree.imanager.model;

import javafx.collections.ObservableList;
import onlythree.imanager.model.tag.Tag;
import onlythree.imanager.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of a task list
 */
public interface ReadOnlyTaskList {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
