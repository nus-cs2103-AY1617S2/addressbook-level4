package project.taskcrusher.model;

import javafx.collections.ObservableList;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.tag.Tag;
import project.taskcrusher.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyUserInbox {

    /**
     * Returns an unmodifiable view of the task list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the event list.
     * This list will not contain any duplicate events.
     */
    ObservableList<ReadOnlyEvent> getEventList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();


}
