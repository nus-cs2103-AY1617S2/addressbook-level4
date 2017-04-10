package seedu.tache.model;

import javafx.collections.ObservableList;
import seedu.tache.model.tag.Tag;
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
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    //@@author A0142255M
    /**
     * Returns a string that contains the no. of tasks and no. of tags.
     */
    String toString();

}
