package seedu.doist.model;

import javafx.collections.ObservableList;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an to-do list
 */
public interface ReadOnlyTodoList {

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
