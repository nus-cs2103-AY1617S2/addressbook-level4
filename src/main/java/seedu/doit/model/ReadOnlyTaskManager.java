package seedu.doit.model;


import javafx.collections.ObservableList;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.tag.Tag;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the task s list.
     * This list will not contain any duplicate task s.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
