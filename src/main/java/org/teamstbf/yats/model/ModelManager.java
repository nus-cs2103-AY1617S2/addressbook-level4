package org.teamstbf.yats.model;

import java.util.ArrayList;
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
import org.teamstbf.yats.model.item.UniqueEventList.DuplicateEventException;
import org.teamstbf.yats.model.item.UniqueEventList.EventNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
	interface Expression {
		boolean satisfies(ReadOnlyEvent event);

		@Override
		String toString();
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

	interface Qualifier {
		boolean run(ReadOnlyEvent event);

		@Override
		String toString();
	}

	private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

	private final TaskManager taskManager;
	
	private static Stack<TaskManager> undoTaskManager = new Stack<TaskManager>();
    private static Stack<TaskManager> redoTaskManager = new Stack<TaskManager>();

	private final FilteredList<ReadOnlyEvent> filteredEvents;

	private ObservableList<ReadOnlyEvent> observedEvents;
	private SortedList<ReadOnlyEvent> sortedEvents;
	private boolean toSort = false;

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
		observedEvents = FXCollections.observableList(this.taskManager.getTaskList());
		filteredEvents = observedEvents.filtered(null);
		sortedEvents = observedEvents.sorted();
	}

	@Override
	public synchronized void addEvent(Event event) throws UniqueEventList.DuplicateEventException {
	    saveImage();
	    taskManager.addEvent(event);
		updateFilteredListToShowAll();
		indicateTaskManagerChanged();
	}

    private void saveImage() {
        TaskManager tempManager = new TaskManager();
	    tempManager.resetData(taskManager);
	    undoTaskManager.push(tempManager);
	    redoTaskManager = new Stack<TaskManager>();
    }

	@Override
	public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
	    saveImage();
		taskManager.removeEvent(target);
		indicateTaskManagerChanged();
	}
	
	
	//@@author A0102778B
	
	@Override
    public synchronized void getPreviousState() {
	    TaskManager tempManager = new TaskManager();
	    tempManager.resetData(taskManager);
	    redoTaskManager.push(tempManager);
        taskManager.resetData((ReadOnlyTaskManager)undoTaskManager.pop());
    }
	
    @Override
    public synchronized void getNextState() {
        saveImage();
        taskManager.resetData((ReadOnlyTaskManager)redoTaskManager.pop());
    }

	@Override
	public UnmodifiableObservableList<ReadOnlyEvent> getFilteredTaskList() {
		return new UnmodifiableObservableList<>(filteredEvents);
	}

	// =========== Filtered Event List Accessors
	// =============================================================

	@Override
	public ReadOnlyTaskManager getTaskManager() {
		return taskManager;
	}

	/** Raises an event to indicate the model has changed */
	private void indicateTaskManagerChanged() {
		raise(new TaskManagerChangedEvent(taskManager));
	}

	@Override
	public void resetData(ReadOnlyTaskManager newData) {
	    saveImage();
		taskManager.resetData(newData);
		indicateTaskManagerChanged();
	}

	@Override
	public void sortFilteredEventList() {
		SortedList<ReadOnlyEvent> sortedEventList = new SortedList<ReadOnlyEvent>(filteredEvents);
		sortedEventList.sorted();
	}

	@Override
	public void updateEvent(int filteredEventListIndex, Event editedEvent) throws DuplicateEventException {
		// TODO Auto-generated method stub
	}

	// ========== Inner classes/interfaces used for filtering
	// =================================================

	@Override
	public void updateEvent(int filteredEventListIndex, ReadOnlyEvent editedEvent)
			throws UniqueEventList.DuplicateEventException {
		assert editedEvent != null;
		saveImage();
		int taskManagerIndex = filteredEvents.getSourceIndex(filteredEventListIndex);
		taskManager.updateEvent(taskManagerIndex, editedEvent);
		indicateTaskManagerChanged();
	}


	@Override
	public UnmodifiableObservableList<ReadOnlyEvent> getSortedTaskList() {
		return new UnmodifiableObservableList<ReadOnlyEvent>(sortedEvents);
	}

	@Override
	public void updateFilteredListToShowAll() {
		filteredEvents.setPredicate(null);
=======
	private void updateFilteredEventList(Expression expression) {
		filteredEvents.setPredicate(expression::satisfies);
	}

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
	public void updateFilteredListToShowTags(Set<String> keywords) {
		updateFilteredEventList(new PredicateExpression(new TagQualifier(keywords)));
	}

	@Override
	public void updateFilteredEventList(Set<String> keywords) {
		updateFilteredEventList(new PredicateExpression(new NameQualifier(keywords)));
	}

	@Override
	public void sortFilteredEventList() {
		filteredEvents.sorted();
	}

	// =========== Sorted Event List Accessors
	// =============================================================

	@Override
	public void setToSortListSwitch() {
		this.toSort = true;
	}

	@Override
	public void unSetToSortListSwitch() {
		this.toSort = false;
	}

	@Override
	public boolean getSortListSwitch() {
		return toSort;
	}

	@Override
	public void updateSortedEventList() {
		updateSortedEventListByTitle();
	}

	private void updateSortedEventListByTitle() {
		sortedEvents.sorted();
	}

	// ========== Inner classes/interfaces used for filtering
	// =================================================

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

	interface Qualifier {
		boolean run(ReadOnlyEvent event);

		@Override
		String toString();
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
					keyword -> StringUtil.containsWordIgnoreCase(event.getStartTime().getDate().toString(), keyword))
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
					keyword -> StringUtil.containsWordIgnoreCase(event.getStartTime().getTime().toString(), keyword))
					.findAny().isPresent();
		}

		@Override
		public String toString() {
			return "startTime=" + String.join(", ", startTimeKeyWords);
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

	// ========== Inner classes/interfaces used for sorting
	// =================================================
	// TODO: include comparable and comparator classes and interface for sorting
	// by respective attributes
}
