package todolist.model;


import javafx.collections.ObservableList;
import todolist.model.tag.Tag;
import todolist.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an to-do list
 */
public interface ReadOnlyToDoList {

    /**
     * Returns an unmodifiable view of the Tasks list.
     * This list will not contain any duplicate Tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
