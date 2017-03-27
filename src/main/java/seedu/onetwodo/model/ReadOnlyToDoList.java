package seedu.onetwodo.model;

import javafx.collections.ObservableList;
import seedu.onetwodo.model.tag.Tag;
import seedu.onetwodo.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an toDo list
 */
public interface ReadOnlyToDoList {

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
