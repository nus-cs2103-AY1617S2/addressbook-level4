package org.teamstbf.yats.model;

import java.time.LocalDate;
import java.util.Set;

import org.teamstbf.yats.commons.core.UnmodifiableObservableList;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.UniqueEventList;

/**
 * The API of the Model component.
 */
public interface Model {

	/** Adds the given Event */
	void addEvent(Event event);

	/** Deletes the given Event. */
	void deleteEvent(ReadOnlyEvent target) throws UniqueEventList.EventNotFoundException;

	/**
	 * Returns the filtered event list as an
	 * {@code UnmodifiableObservableList<ReadOnlyEvent>}
	 */
	UnmodifiableObservableList<ReadOnlyEvent> getFilteredTaskList();

	/**
	 * Returns the calendar filtered event list as an
	 * {@code UnmodifiableObservableList<ReadOnlyEvent>}
	 */
	UnmodifiableObservableList<ReadOnlyEvent> getCalendarFilteredTaskList();

	/**
	 * Returns the task filtered event list as an
	 * {@code UnmodifiableObservableList<ReadOnlyEvent>}
	 */
	UnmodifiableObservableList<ReadOnlyEvent> getTaskFilteredTaskList();

	/** Returns the TaskManager */
	ReadOnlyTaskManager getTaskManager();

	/**
	 * Clears existing backing model and replaces with the provided new data.
	 */
	void resetData(ReadOnlyTaskManager newData);

	/**
	 * Updates the event located at {@code filteredEventListIndex} with
	 * {@code editedEvent}.
	 *
	 * @throws IndexOutOfBoundsException
	 *             if {@code filteredEventListIndex} < 0 or >= the size of the
	 *             filtered list.
	 */
	void updateEvent(int filteredEventListIndex, ReadOnlyEvent editedEvent);

	/**
	 * Updates the filter of the filtered event list to filter by the given
	 * keywords
	 */
	void updateFilteredEventList(Set<String> keywords);

	/** Updates the filter of the filtered event list to show all events */
	void updateFilteredListToShowAll();

	// @@author A0139448U
	/*
	 * force saves the current state of the taskmanager for use in changing save
	 * location to create a file in new location
	 */
	void saveTaskManager();

	// @@author A0138952W
	/**
	 * Updates the filter of the Calendar filtered event list to show specified
	 * start time
	 *
	 * @param today
	 */
	void updateCalendarFilteredListToShowStartTime(LocalDate today);

	/**
	 * Updates the filter of the Done task filtered event list to show done task
	 */
	void updateDoneTaskList();

	/**
	 * Updates the filter of the filtered event list to show specified location
	 */
	void updateFilteredListToShowLocation(Set<String> keywords);

	/** Updates the filter of the filtered event list to show specified date */
	void updateFilteredListToShowEndTime(Set<String> keywords);

	/**
	 * Updates the filter of the filtered event list to show specified start
	 * time
	 */
	void updateFilteredListToShowStartTime(Set<String> keywords);

	/**
	 * Updates the filter of the filtered event list to show specified deadline
	 */
	void updateFilteredListToShowDeadline(Set<String> keywords);

	/** Updates the filter of the filtered event list to show done tasks */
	void updateFilteredListToShowDone(Set<String> keywords);

	/** Updates the filter of the filtered event list to show specified tags */
	void updateFilteredListToShowTags(Set<String> keywords);

	/**
	 * Updates the filter of the filtered event list to search for all tasks
	 * including description with the specified keywords
	 */
	void updateFilteredListToFindAll(Set<String> keywords);

	// @@author A0102778B
	/**
	 * Method to get the previous state (undo command) of the task manager
	 */
	void getPreviousState();

	/**
	 * Method to get the next state (redo command) of the task manager
	 */
	void getNextState();

	/**
	 * Method to check if the undo stack is empty - nothing to undo
	 */
	boolean checkEmptyUndoStack();

	/**
	 * Method to check if the redo stack is empty - nothing to redo
	 */
	boolean checkEmptyRedoStack();

	/**
	 * Method to check if the redo stack is empty - nothing to redo
	 */
	void scheduleEvent(Event event);

	/**
	 * Saves an image of the previous state of the TaskManager for the undo
	 * command - also clears the redo stack images because once the state is
	 * mutated the previous redoes state are invalid because they are no longer
	 * part of the same chain. This is an internal method used by the addEvent,
	 * deteleEvent, clearEvent, editEvent methods. This method also contains a
	 * check - if there are currently too many task manager states, it will
	 * remove half of the earlier saved states and only keep the later half.
	 */

	void saveImageOfCurrentTaskManager();

	void updateFilteredListToShowSortedStart();

	void updateFilteredListToShowSortedEnd();

	void updateFilteredListToShowDeadline();

	void updateFilteredListToShowDone();

}
