package org.teamstbf.yats.model;

import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.tag.Tag;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the events list. This list will not
     * contain any duplicate events.
     */
    ObservableList<ReadOnlyEvent> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list. This list will not contain
     * any duplicate tags.
     */
    ObservableList<Tag> getTagList();
}
