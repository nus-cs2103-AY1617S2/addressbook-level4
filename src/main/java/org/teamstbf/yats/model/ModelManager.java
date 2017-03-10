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
import org.teamstbf.yats.model.item.UniqueItemList;
import org.teamstbf.yats.model.item.UniqueItemList.DuplicatePersonException;
import org.teamstbf.yats.model.item.UniqueItemList.PersonNotFoundException;

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
		filteredPersons = new FilteredList<>(this.taskManager.getPersonList());
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
	public ReadOnlyTaskManager getAddressBook() {
		return taskManager;
	}

	/** Raises an event to indicate the model has changed */
	private void indicateAddressBookChanged() {
		raise(new TaskManagerChangedEvent(taskManager));
	}

	@Override
	public synchronized void deletePerson(ReadOnlyEvent target) throws PersonNotFoundException {
		taskManager.removePerson(target);
		indicateAddressBookChanged();
	}

	@Override
	public synchronized void addEvent(Event event) throws UniqueItemList.DuplicatePersonException {
		taskManager.addEvent(event);
		updateFilteredListToShowAll();
		indicateAddressBookChanged();
	}

	@Override
	public void updatePerson(int filteredPersonListIndex, ReadOnlyEvent editedPerson)
			throws UniqueItemList.DuplicatePersonException {
		assert editedPerson != null;

		int addressBookIndex = filteredPersons.getSourceIndex(filteredPersonListIndex);
		taskManager.updatePerson(addressBookIndex, editedPerson);
		indicateAddressBookChanged();
	}

	@Override
	public void updatePerson(int filteredPersonListIndex, Event editedPerson) throws DuplicatePersonException {
		// TODO Auto-generated method stub
	}

	//=========== Filtered Person List Accessors =============================================================

	@Override
	public UnmodifiableObservableList<ReadOnlyEvent> getFilteredTaskList() {
		return new UnmodifiableObservableList<>(filteredPersons);
	}

	@Override
	public void updateFilteredListToShowAll() {
		filteredPersons.setPredicate(null);
	}

	@Override
	public void updateFilteredPersonList(Set<String> keywords) {
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
