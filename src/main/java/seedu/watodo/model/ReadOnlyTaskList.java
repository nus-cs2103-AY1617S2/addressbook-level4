package seedu.watodo.model;


import javafx.collections.ObservableList;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.task.ReadOnlyFloatingTask;

/**
 * Unmodifiable view of a task list
 */
public interface ReadOnlyTaskList {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyFloatingTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
