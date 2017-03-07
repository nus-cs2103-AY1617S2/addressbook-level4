package seedu.geekeep.model;

import java.util.Set;

import seedu.geekeep.commons.core.UnmodifiableObservableList;
import seedu.geekeep.model.person.Person;
import seedu.geekeep.model.person.ReadOnlyPerson;
import seedu.geekeep.model.person.UniquePersonList;
import seedu.geekeep.model.person.UniquePersonList.DuplicatePersonException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the AddressBook */
    ReadOnlyTaskManager getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws UniquePersonList.PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws UniquePersonList.DuplicatePersonException;

    /**
     * Updates the person located at {@code filteredPersonListIndex} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException
     *             if updating the person's details causes the person to be equivalent to another existing person in the
     *             list.
     * @throws IndexOutOfBoundsException
     *             if {@code filteredPersonListIndex} < 0 or >= the size of the filtered list.
     */
    void updatePerson(int filteredPersonListIndex, ReadOnlyPerson editedPerson)
            throws UniquePersonList.DuplicatePersonException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords */
    void updateFilteredPersonList(Set<String> keywords);

}
