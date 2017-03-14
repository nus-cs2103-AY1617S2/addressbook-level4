package seedu.doit.model;


import javafx.collections.ObservableList;
import seedu.doit.model.item.ReadOnlyFloatingTask;
import seedu.doit.model.tag.Tag;

/**
 * Unmodifiable view of an floatingTask manager
 */
public interface ReadOnlyFloatingTaskManager {

    /**
     * Returns an unmodifiable view of the floatingTask s list.
     * This list will not contain any duplicate floatingTask s.
     */
    ObservableList<ReadOnlyFloatingTask> getFloatingTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
