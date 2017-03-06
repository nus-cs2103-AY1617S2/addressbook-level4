package seedu.geekeep.model;

import javafx.collections.ObservableList;
import seedu.geekeep.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {
    /**
     * Returns an unmodifiable view of the task list. This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyTask> getTaskList();
}
