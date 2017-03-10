package org.teamstbf.yats.model;

import java.util.Set;

import org.teamstbf.yats.commons.core.UnmodifiableObservableList;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Task;
import org.teamstbf.yats.model.item.UniqueItemList;
import org.teamstbf.yats.model.item.UniqueItemList.DuplicatePersonException;

/**
 * The API of the Model component.
 */
public interface Model {
	/** Clears existing backing model and replaces with the provided new data. */
	void resetData(ReadOnlyTaskManager newData);

	/** Returns the AddressBook */
	ReadOnlyTaskManager getAddressBook();

	/** Deletes the given person. */
	void deletePerson(ReadOnlyEvent target) throws UniqueItemList.PersonNotFoundException;

	/** Adds the given event */
	void addEvent(Event event) throws UniqueItemList.DuplicatePersonException;

	/**
	 * Updates the person located at {@code filteredPersonListIndex} with {@code editedPerson}.
	 *
	 * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
	 *      another existing person in the list.
	 * @throws IndexOutOfBoundsException if {@code filteredPersonListIndex} < 0 or >= the size of the filtered list.
	 */
	void updatePerson(int filteredPersonListIndex, ReadOnlyEvent editedPerson)
			throws UniqueItemList.DuplicatePersonException;

	void updatePerson(int filteredPersonListIndex, Event editedPerson)
			throws DuplicatePersonException;

	/** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
	UnmodifiableObservableList<ReadOnlyEvent> getFilteredTaskList();

	/** Updates the filter of the filtered person list to show all persons */
	void updateFilteredListToShowAll();

	/** Updates the filter of the filtered person list to filter by the given keywords*/
	void updateFilteredPersonList(Set<String> keywords);


}
