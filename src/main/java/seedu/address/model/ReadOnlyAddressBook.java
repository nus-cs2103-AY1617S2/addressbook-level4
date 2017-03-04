package seedu.address.model;


import javafx.collections.ObservableList;
import seedu.address.model.label.Label;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyPerson> getPersonList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Label> getTagList();

}
