package org.teamstbf.yats.model;

import java.util.Set;

import org.teamstbf.yats.commons.core.UnmodifiableObservableList;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.UniqueEventList;
import org.teamstbf.yats.model.item.UniqueEventList.DuplicateEventException;

/**
 * The API of the Model component.
 */
public interface Model {
	/** Adds the given Event */
	void addEvent(Event event) throws UniqueEventList.DuplicateEventException;

	/** Deletes the given Event. */
	void deleteEvent(ReadOnlyEvent target) throws UniqueEventList.EventNotFoundException;

	/**
	 * Returns the filtered event list as an
	 * {@code UnmodifiableObservableList<ReadOnlyEvent>}
	 */
	UnmodifiableObservableList<ReadOnlyEvent> getFilteredTaskList();

	/** Returns the TaskManager */
	ReadOnlyTaskManager getTaskManager();

	/**
	 * Clears existing backing model and replaces with the provided new data.
	 */
	void resetData(ReadOnlyTaskManager newData);

	/** Sorts the filtered event list */
	void sortFilteredEventList();

	void updateEvent(int filteredEventListIndex, Event editedEvent) throws DuplicateEventException;

	/**
	 * Updates the event located at {@code filteredEventListIndex} with
	 * {@code editedEvent}.
	 *
	 * @throws DuplicateEventException
	 *             if updating the event's details causes the event to be
	 *             equivalent to another existing event in the list.
	 * @throws IndexOutOfBoundsException
	 *             if {@code filteredEventListIndex} < 0 or >= the size of the
	 *             filtered list.
	 */
	void updateEvent(int filteredEventListIndex, ReadOnlyEvent editedEvent)
			throws UniqueEventList.DuplicateEventException;

	/**
	 * Updates the filter of the filtered event list to filter by the given
	 * keywords
	 */
	void updateFilteredEventList(Set<String> keywords);

	/** Updates the filter of the filtered event list to show all events */
	void updateFilteredListToShowAll();

}
