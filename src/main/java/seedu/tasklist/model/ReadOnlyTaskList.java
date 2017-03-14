package seedu.tasklist.model;

import javafx.collections.ObservableList;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an task list
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
