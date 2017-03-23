package seedu.geekeep.model;

import javafx.collections.ObservableList;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the persons list. This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list. This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
