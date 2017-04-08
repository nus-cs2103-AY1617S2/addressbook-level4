package seedu.taskit.model;


import javafx.collections.ObservableList;
import seedu.taskit.model.tag.Tag;
import seedu.taskit.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**
    * Returns an unmodifiable view of the tasks list.
    * This list will not contain any duplicate tasks.
    */
    ObservableList<ReadOnlyTask> getTaskList();

}
