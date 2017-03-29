package org.teamstbf.yats.model;

import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.teamstbf.yats.commons.core.ComponentManager;
import org.teamstbf.yats.commons.core.LogsCenter;
import org.teamstbf.yats.commons.core.UnmodifiableObservableList;
import org.teamstbf.yats.commons.events.model.TaskManagerChangedEvent;
import org.teamstbf.yats.commons.util.CollectionUtil;
import org.teamstbf.yats.commons.util.StringUtil;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.UniqueEventList;
import org.teamstbf.yats.model.item.UniqueEventList.EventNotFoundException;

import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {

	private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

	private static final int MAXIMUM_SIZE_OF_UNDO_STACK = 5;

	private static final int NEW_SIZE_OF_UNDO_STACK_AFTER_RESIZE = 5;

	private final TaskManager taskManager;

	// @@author A0102778B

	private static Stack<TaskManager> undoTaskManager = new Stack<TaskManager>();
	private static Stack<TaskManager> redoTaskManager = new Stack<TaskManager>();

	private final FilteredList<ReadOnlyEvent> filteredEvents;

	public ModelManager() {
		this(new TaskManager(), new UserPrefs());
	}

	/**
	 * Initializes a ModelManager with the given taskManager and userPrefs.
	 */
	public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
		super();
		assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

		logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

		this.taskManager = new TaskManager(taskManager);
		filteredEvents = new FilteredList<>(this.taskManager.getTaskList());
		undoTaskManager = new Stack<TaskManager>();
		redoTaskManager = new Stack<TaskManager>();
	}

	@Override
	public synchronized void addEvent(Event event) throws UniqueEventList.DuplicateEventException {
		saveImageOfCurrentTaskManager();
		taskManager.addEvent(event);
		updateFilteredListToShowAll();
		indicateTaskManagerChanged();
	}

	/**
	 * Saves an image of the previous state of the TaskManager for the undo
	 * command - also clears the redo stack images because once the state is
	 * mutated the previous redoes state are invalid because they are no longer
	 * part of the same chain. This is an internal method used by the addEvent,
	 * deteleEvent, clearEvent, editEvent methods. This method also contains a
	 * check - if there are currently too many task manager states, it will
	 * remove half of the earlier saved states and only keep the later half.
	 */
	private void saveImageOfCurrentTaskManager() {
		removeUndoEntriesIfUndoStackSizeTooLarge();
		TaskManager tempManager = new TaskManager();
		tempManager.resetData(taskManager);
		undoTaskManager.push(tempManager);
		clearRedoStack();
	}

	/**
	 * This method clears the redo stack of taskmanagers. This occurs when a new
	 * arraylist is created.
	 */
	private void clearRedoStack() {
		redoTaskManager = new Stack<TaskManager>();
	}

	/**
	 * This method checks if the undo stack size is above the maximum allowed
	 * size
	 */
	private void removeUndoEntriesIfUndoStackSizeTooLarge() {
		if (undoTaskManager.size() >= MAXIMUM_SIZE_OF_UNDO_STACK) {
			removeHalfOfUndoStack(undoTaskManager);
		}
	}

	/**
	 * This method removes half of a stack of TaskManagers given to it. TODO-
	 * test this method
	 */
	private void removeHalfOfUndoStack(Stack<TaskManager> currStack) {
		Stack<TaskManager> tempUndoTaskManager = new Stack<TaskManager>();
		for (int i = 0; i < NEW_SIZE_OF_UNDO_STACK_AFTER_RESIZE; i++) {
			tempUndoTaskManager.push(undoTaskManager.pop());
		}
		while (!undoTaskManager.isEmpty()) {
			undoTaskManager.pop();
		}
		while (!tempUndoTaskManager.isEmpty()) {
			undoTaskManager.push(tempUndoTaskManager.pop());
		}
	}

	@Override
	public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
		saveImageOfCurrentTaskManager();
		taskManager.removeEvent(target);
		indicateTaskManagerChanged();
	}

	//@@author A0102778B

	@Override
	public boolean checkEmptyUndoStack() {
		return undoTaskManager.isEmpty();
	}

	@Override
	public boolean checkEmptyRedoStack() {
		return redoTaskManager.isEmpty();
	}

	@Override
	public synchronized void getPreviousState() {
		TaskManager tempManager = new TaskManager();
		tempManager.resetData(taskManager);
		redoTaskManager.push(tempManager);
		taskManager.resetData(undoTaskManager.pop());
		indicateTaskManagerChanged();
	}

	@Override
	public synchronized void getNextState() {
		TaskManager tempManager = new TaskManager();
		tempManager.resetData(taskManager);
		undoTaskManager.push(tempManager);
		taskManager.resetData(redoTaskManager.pop());
		indicateTaskManagerChanged();
	}
	
	// @@author 
	
	/*
	 * @Override public synchronized void getNextState() { saveImage();
	 * taskManager.resetData(redoTaskManager.pop()); }
	 */

	@Override
	public ReadOnlyTaskManager getTaskManager() {
		return taskManager;
	}

	// =========== Filtered Event List Accessors
	// =============================================================

	@Override
	public UnmodifiableObservableList<ReadOnlyEvent> getFilteredTaskList() {
		return new UnmodifiableObservableList<>(filteredEvents);
	}

	/** Raises an event to indicate the model has changed */
	private void indicateTaskManagerChanged() {
		raise(new TaskManagerChangedEvent(taskManager));
	}

	@Override
	public void resetData(ReadOnlyTaskManager newData) {
		saveImageOfCurrentTaskManager();
		taskManager.resetData(newData);
		indicateTaskManagerChanged();
	}

	// ========== Inner classes/interfaces used for filtering
	// =================================================

	@Override
	public void updateEvent(int filteredEventListIndex, ReadOnlyEvent editedEvent)
			throws UniqueEventList.DuplicateEventException {
		assert editedEvent != null;
		saveImageOfCurrentTaskManager();
		int taskManagerIndex = filteredEvents.getSourceIndex(filteredEventListIndex);
		taskManager.updateEvent(taskManagerIndex, editedEvent);
		indicateTaskManagerChanged();
	}

	private void updateFilteredEventList(Expression expression) {
		filteredEvents.setPredicate(expression::satisfies);
	}

	@Override
	public void updateFilteredEventList(Set<String> keywords) {
		updateFilteredEventList(new PredicateExpression(new NameQualifier(keywords)));
	}

	@Override
	public void updateFilteredListToShowAll() {
		filteredEvents.setPredicate(null);
	}

	// @@author A0138952W
	@Override
	public void updateFilteredListToShowLocation(Set<String> keywords) {
		updateFilteredEventList(new PredicateExpression(new LocationQualifier(keywords)));
	}

	@Override
	public void updateFilteredListToShowDate(Set<String> keywords) {
		updateFilteredEventList(new PredicateExpression(new DateQualifier(keywords)));
	}

	@Override
	public void updateFilteredListToShowStartTime(Set<String> keywords) {
		updateFilteredEventList(new PredicateExpression(new StartTimeQualifier(keywords)));
	}

	@Override
	public void updateFilteredListToShowDone(Set<String> keywords) {
		updateFilteredEventList(new PredicateExpression(new DoneQualifier(keywords)));
	}

	@Override
	public void updateFilteredListToShowTags(Set<String> keywords) {
		updateFilteredEventList(new PredicateExpression(new TagQualifier(keywords)));
	}

	@Override
	public void updateFilteredListToFindAll(Set<String> keywords) {
		updateFilteredEventList(new PredicateExpression(new FindQualifier(keywords)));
	}

	// Inner class used for Searching //
	interface Qualifier {
		boolean run(ReadOnlyEvent event);

		@Override
		String toString();
	}

	interface Expression {
		boolean satisfies(ReadOnlyEvent event);

		@Override
		String toString();
	}

	private class PredicateExpression implements Expression {

		private final Qualifier qualifier;

		PredicateExpression(Qualifier qualifier) {
			this.qualifier = qualifier;
		}

		@Override
		public boolean satisfies(ReadOnlyEvent event) {
			return qualifier.run(event);
		}

		@Override
		public String toString() {
			return qualifier.toString();
		}
	}

	private class NameQualifier implements Qualifier {
		private Set<String> nameKeyWords;

		NameQualifier(Set<String> nameKeyWords) {
			this.nameKeyWords = nameKeyWords;
		}

		@Override
		public boolean run(ReadOnlyEvent event) {
			return nameKeyWords.stream()
					.filter(keyword -> StringUtil.containsWordIgnoreCase(event.getTitle().fullName, keyword)).findAny()
					.isPresent();
		}

		@Override
		public String toString() {
			return "title=" + String.join(", ", nameKeyWords);
		}
	}

	// @@author A0138952W
	private class LocationQualifier implements Qualifier {

		private Set<String> locationKeyWords;

		LocationQualifier(Set<String> locationKeyWords) {
			this.locationKeyWords = locationKeyWords;
		}

		@Override
		public boolean run(ReadOnlyEvent event) {
			return locationKeyWords.stream()
					.filter(keyword -> StringUtil.containsWordIgnoreCase(event.getLocation().toString(), keyword))
					.findAny().isPresent();
		}

		@Override
		public String toString() {
			return "location=" + String.join(", ", locationKeyWords);
		}
	}

	private class DateQualifier implements Qualifier {

		private Set<String> dateKeyWords;

		DateQualifier(Set<String> dateKeyWords) {
			this.dateKeyWords = dateKeyWords;
		}

		@Override
		public boolean run(ReadOnlyEvent event) {
			return dateKeyWords.stream().filter(
					keyword -> StringUtil.containsWordIgnoreCase(event.getStartTime().toString(), keyword))
					.findAny().isPresent();
		}

		@Override
		public String toString() {
			return "date=" + String.join(", ", dateKeyWords);
		}
	}

	private class StartTimeQualifier implements Qualifier {

		private Set<String> startTimeKeyWords;

		StartTimeQualifier(Set<String> startTimeKeyWords) {
			this.startTimeKeyWords = startTimeKeyWords;
		}

		@Override
		public boolean run(ReadOnlyEvent event) {
			return startTimeKeyWords.stream().filter(
					keyword -> StringUtil.containsWordIgnoreCase(event.getStartTime().toString(), keyword))
					.findAny().isPresent();
		}

		@Override
		public String toString() {
			return "startTime=" + String.join(", ", startTimeKeyWords);
		}
	}

	private class DoneQualifier implements Qualifier {

		private Set<String> doneKeyWords;

		DoneQualifier(Set<String> doneKeyWords) {
			this.doneKeyWords = doneKeyWords;
		}

		@Override
		public boolean run(ReadOnlyEvent event) {
			return doneKeyWords.stream()
					.filter(keyword -> StringUtil.containsWordIgnoreCase(event.getIsDone().getValue(), keyword)).findAny()
					.isPresent();
		}

		@Override
		public String toString() {
			return "done=" + String.join(", ", doneKeyWords);
		}
	}

	private class TagQualifier implements Qualifier {

		private Set<String> tagKeyWords;

		TagQualifier(Set<String> tagKeyWords) {
			this.tagKeyWords = tagKeyWords;
		}

		@Override
		public boolean run(ReadOnlyEvent event) {
			String tagObtain = event.getTags().asObservableList().stream().map(tagString -> tagString.tagName)
					.distinct().collect(Collectors.joining(" "));
			return tagKeyWords.stream().filter(keyword -> StringUtil.containsWordIgnoreCase(tagObtain, keyword))
					.findAny().isPresent();
		}

		@Override
		public String toString() {
			return "tag=" + String.join(", ", tagKeyWords);
		}
	}

	private class FindQualifier implements Qualifier {

		private int SUBSTRING_INDEX = 0;
		private Set<String> findKeyWords;

		FindQualifier(Set<String> findKeyWords) {
			this.findKeyWords = findKeyWords;
		}

		@Override
		public boolean run(ReadOnlyEvent event) {
			return findKeyWords.stream().filter(keyword -> {
				if (StringUtil.containsWordIgnoreCase(event.getDescription().value.substring(SUBSTRING_INDEX),
						keyword)) {
					return true;
				} else if (StringUtil.containsWordIgnoreCase(event.getTitle().fullName.substring(0), keyword)) {
					return true;
				} else if (StringUtil.containsWordIgnoreCase(event.getDescription().value.substring(0), keyword)) {
					return (StringUtil.containsWordIgnoreCase(event.getTitle().fullName.substring(0), keyword));
				} else if (StringUtil.containsWordIgnoreCase(event.getTitle().fullName.substring(0), keyword)) {
					return (StringUtil.containsWordIgnoreCase(event.getDescription().value.substring(0), keyword));
				}
				return false;
			}).findAny().isPresent();
		}

		@Override
		public String toString() {
			return "search=" + String.join(", ", findKeyWords);
		}
	}

}
