package seedu.address.model;


import javafx.collections.ObservableList;
import seedu.address.model.label.Label;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of a task manager
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the labels list.
     * This list will not contain any duplicate labels.
     */
    ObservableList<Label> getLabelList();

}
