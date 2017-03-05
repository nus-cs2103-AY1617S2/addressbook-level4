package seedu.address.model;


import javafx.collections.ObservableList;
import seedu.address.model.label.Label;
import seedu.address.model.person.ReadOnlyTask;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyTask> getPersonList();

    /**
     * Returns an unmodifiable view of the labels list.
     * This list will not contain any duplicate labels.
     */
    ObservableList<Label> getLabelList();

}
