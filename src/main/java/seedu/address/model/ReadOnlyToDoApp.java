package seedu.address.model;


import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of a ToDoApp
 */
public interface ReadOnlyToDoApp {

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
