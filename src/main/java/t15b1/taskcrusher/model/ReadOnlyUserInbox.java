package t15b1.taskcrusher.model;


import javafx.collections.ObservableList;
import t15b1.taskcrusher.model.tag.Tag;
import t15b1.taskcrusher.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyUserInbox {

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
