package seedu.doit.model;


import javafx.collections.ObservableList;
import seedu.doit.model.item.ReadOnlyEvent;
import seedu.doit.model.tag.Tag;

/**
 * Unmodifiable view of an event manager
 */
public interface ReadOnlyEventManager {

    /**
     * Returns an unmodifiable view of the event s list.
     * This list will not contain any duplicate event s.
     */
    ObservableList<ReadOnlyEvent> getEventList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
