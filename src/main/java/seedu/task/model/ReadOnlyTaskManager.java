package seedu.task.model;


import javafx.collections.ObservableList;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.ReadOnlyTask;

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
    ObservableList<Tag> getTagList();

}
