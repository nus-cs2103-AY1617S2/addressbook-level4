package seedu.whatsleft.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.model.tag.Tag;

/**
 * Unmodifiable view of WhatsLeft
 */
public interface ReadOnlyWhatsLeft {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the event list.
     * This list will not contain any duplicate events.
     */
    List<ReadOnlyEvent> getEventList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();
}
