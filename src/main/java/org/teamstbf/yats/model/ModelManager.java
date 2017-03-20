package org.teamstbf.yats.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

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
	
	private static final Stack<TaskManager> undoTaskManager = new Stack<TaskManager>();
    private static final Stack<TaskManager> redoTaskManager = new Stack<TaskManager>();

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

}
