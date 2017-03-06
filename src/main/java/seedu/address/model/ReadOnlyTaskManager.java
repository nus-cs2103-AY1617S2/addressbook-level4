package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.tag.Tag;

public interface ReadOnlyTaskManager {
    
    /**
     * Returns an unmodifiable view of the tasks list.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
