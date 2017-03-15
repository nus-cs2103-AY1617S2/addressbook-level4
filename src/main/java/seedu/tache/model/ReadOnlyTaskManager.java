package seedu.tache.model;

import javafx.collections.ObservableList;
import seedu.tache.model.tag.Tag;
import seedu.tache.model.task.ReadOnlyDetailedTask;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the detailed tasks list.
     * This list will not contain any duplicate detailed tasks.
     */
    ObservableList<ReadOnlyDetailedTask> getDetailedTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
