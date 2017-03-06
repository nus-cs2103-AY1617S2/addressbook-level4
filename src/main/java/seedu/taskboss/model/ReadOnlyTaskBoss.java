package seedu.taskboss.model;


import javafx.collections.ObservableList;
import seedu.taskboss.model.category.Tag;
import seedu.taskboss.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of TaskBoss
 */
public interface ReadOnlyTaskBoss {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
