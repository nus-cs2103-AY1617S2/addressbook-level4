package seedu.bulletjournal.model;

import javafx.collections.ObservableList;
import seedu.bulletjournal.model.tag.Tag;
import seedu.bulletjournal.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTodoList {

    /**
     * Returns an unmodifiable view of the tasks list. This list will not
     * contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTodoList();

    /**
     * Returns an unmodifiable view of the tags list. This list will not contain
     * any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
