package t16b4.yats.model;

import java.util.Set;

import t16b4.yats.commons.core.UnmodifiableObservableList;
import t16b4.yats.model.item.Task;
import t16b4.yats.model.item.ReadOnlyItem;
import t16b4.yats.model.item.UniqueItemList;
import t16b4.yats.model.item.UniqueItemList.DuplicatePersonException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the AddressBook */
    ReadOnlyTaskManager getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyItem target) throws UniqueItemList.PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Task person) throws UniqueItemList.DuplicatePersonException;

    /**
     * Updates the person located at {@code filteredPersonListIndex} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws IndexOutOfBoundsException if {@code filteredPersonListIndex} < 0 or >= the size of the filtered list.
     */
    void updatePerson(int filteredPersonListIndex, ReadOnlyItem editedPerson)
            throws UniqueItemList.DuplicatePersonException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyItem> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

}
