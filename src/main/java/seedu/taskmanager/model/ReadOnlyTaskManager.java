package seedu.taskmanager.model;


import javafx.collections.ObservableList;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();
    
//    // @@author A0131278H
//    /**
//     * Returns an unmodifiable view of the tasks list of incomplete tasks.
//     * This list will not contain any duplicate tasks.
//     */
//    ObservableList<ReadOnlyTask> getToDoTaskList();
//    
//    /**
//     * Returns an unmodifiable view of the tasks list of completed tasks.
//     * This list will not contain any duplicate tasks.
//     */
//    ObservableList<ReadOnlyTask> getDoneTaskList();
//    // @@author A0131278H

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
