package seedu.address.model;


import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of WhatsLeft
 */
public interface ReadOnlyWhatsLeft {

    /**
     * Returns an unmodifiable view of the activities list.
     * This list will not contain any duplicate activities.
     */
    ObservableList<ReadOnlyActivity> getActivityList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
