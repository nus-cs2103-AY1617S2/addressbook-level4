package seedu.today.model;

import javafx.collections.ObservableList;
import seedu.today.model.tag.Tag;
import seedu.today.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the tasks list. This list will not
     * contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list. This list will not contain
     * any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
