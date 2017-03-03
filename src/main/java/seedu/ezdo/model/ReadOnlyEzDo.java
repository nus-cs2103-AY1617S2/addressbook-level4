package seedu.ezdo.model;


import javafx.collections.ObservableList;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.todo.ReadOnlyTask;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyEzDo {

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
