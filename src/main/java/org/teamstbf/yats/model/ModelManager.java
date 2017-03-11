package org.teamstbf.yats.model;

import java.util.Set;
import java.util.logging.Logger;

import org.teamstbf.yats.commons.core.ComponentManager;
import org.teamstbf.yats.commons.core.LogsCenter;
import org.teamstbf.yats.commons.core.UnmodifiableObservableList;
import org.teamstbf.yats.commons.events.model.TaskManagerChangedEvent;
import org.teamstbf.yats.commons.util.CollectionUtil;
import org.teamstbf.yats.commons.util.StringUtil;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Task;
import org.teamstbf.yats.model.item.UniqueEventList;
import org.teamstbf.yats.model.item.UniqueEventList.DuplicateEventException;
import org.teamstbf.yats.model.item.UniqueEventList.EventNotFoundException;

import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
	private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

	private final TaskManager taskManager;
	private final FilteredList<ReadOnlyEvent> filteredPersons;

	/**
	 * Initializes a ModelManager with the given addressBook and userPrefs.
	 */
	public ModelManager(ReadOnlyTaskManager addressBook, UserPrefs userPrefs) {
		super();
		assert !CollectionUtil.isAnyNull(addressBook, userPrefs);

		logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

		this.taskManager = new TaskManager(addressBook);
		filteredPersons = new FilteredList<>(this.taskManager.getTaskList());
	}

	public ModelManager() {
		this(new TaskManager(), new UserPrefs());
	}

	@Override
	public void resetData(ReadOnlyTaskManager newData) {
		taskManager.resetData(newData);
		indicateAddressBookChanged();
	}

	@Override
	public ReadOnlyTaskManager getTaskManager() {
		return taskManager;
	}

	/** Raises an event to indicate the model has changed */
	private void indicateAddressBookChanged() {
		raise(new TaskManagerChangedEvent(taskManager));
	}

	@Override
	public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
		taskManager.removePerson(target);
		indicateAddressBookChanged();
	}

	@Override
	public synchronized void addEvent(Event event) throws UniqueEventList.DuplicateEventException {
		taskManager.addEvent(event);
		updateFilteredListToShowAll();
		indicateAddressBookChanged();
	}

	@Override
	public void updateEvent(int filteredPersonListIndex, ReadOnlyEvent editedPerson)
			throws UniqueEventList.DuplicateEventException {
		assert editedPerson != null;

		int addressBookIndex = filteredPersons.getSourceIndex(filteredPersonListIndex);
		taskManager.updatePerson(addressBookIndex, editedPerson);
		indicateAddressBookChanged();
	}

	@Override
	public void updateEvent(int filteredPersonListIndex, Event editedPerson) throws DuplicateEventException {
		// TODO Auto-generated method stub
	}

	//=========== Filtered Person List Accessors =============================================================

	@Override
	public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
		return new UnmodifiableObservableList<>(filteredPersons);
	}

	@Override
	public void updateFilteredListToShowAll() {
		filteredPersons.setPredicate(null);
	}

	@Override
	public void updateFilteredEventList(Set<String> keywords) {
		updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
	}

	private void updateFilteredPersonList(Expression expression) {
		filteredPersons.setPredicate(expression::satisfies);
	}

	//========== Inner classes/interfaces used for filtering =================================================

	interface Expression {
		boolean satisfies(ReadOnlyEvent person);
		String toString();
	}

	private class PredicateExpression implements Expression {

		private final Qualifier qualifier;

		PredicateExpression(Qualifier qualifier) {
			this.qualifier = qualifier;
		}

		@Override
		public boolean satisfies(ReadOnlyEvent person) {
			return qualifier.run(person);
		}

		@Override
		public String toString() {
			return qualifier.toString();
		}
	}

	interface Qualifier {
		boolean run(ReadOnlyEvent person);
		String toString();
	}

	private class NameQualifier implements Qualifier {
		private Set<String> nameKeyWords;

		NameQualifier(Set<String> nameKeyWords) {
			this.nameKeyWords = nameKeyWords;
		}

		@Override
		public boolean run(ReadOnlyEvent person) {
			return nameKeyWords.stream()
					.filter(keyword -> StringUtil.containsWordIgnoreCase(person.getTitle().fullName, keyword))
					.findAny()
					.isPresent();
		}

		@Override
		public String toString() {
			return "name=" + String.join(", ", nameKeyWords);
		}
	}

}
