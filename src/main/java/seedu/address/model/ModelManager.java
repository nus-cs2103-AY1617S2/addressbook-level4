package seedu.address.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicatePersonException;
import seedu.address.model.task.UniqueTaskList.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final YTomorrow addressBook;
    private final History<ReadOnlyTaskManager> history;
    private final FilteredList<ReadOnlyTask> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager addressBook, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new YTomorrow(addressBook);
        this.history = new History<ReadOnlyTaskManager>();
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

        //@@author A0163848R
        history.push(addressBook);
        //@@author
    }

    public ModelManager() {
        this(new YTomorrow(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskManager getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        addToHistory(new YTomorrow(addressBook));
        raise(new TaskManagerChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyTask target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Task person) throws UniqueTaskList.DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(int filteredPersonListIndex, ReadOnlyTask editedPerson)
            throws UniqueTaskList.DuplicatePersonException {
        assert editedPerson != null;

        int addressBookIndex = filteredPersons.getSourceIndex(filteredPersonListIndex);
        addressBook.updatePerson(addressBookIndex, editedPerson);
        indicateAddressBookChanged();
    }

    //@@author A0163848R
    @Override
    public boolean undoLastModification() {
        ReadOnlyTaskManager undone = history.undo();
        if (undone != null) {
            addressBook.resetData(undone);
            return true;
        }
        return false;
    }

    @Override
    public boolean redoLastModification() {
        ReadOnlyTaskManager redone = history.redo();
        if (redone != null) {
            addressBook.resetData(redone);
            return true;
        }
        return false;
    }

    @Override
    public void mergeYTomorrow(ReadOnlyTaskManager add) {
        for (ReadOnlyTask readOnlyTask : add.getPersonList()) {
            Task task = new Task(readOnlyTask);
            try {
                addressBook.addPerson(task);
            } catch (DuplicatePersonException e) {
                try {
                    addressBook.removePerson(task);
                    addressBook.addPerson(task);
                } catch (PersonNotFoundException | DuplicatePersonException el) {
                }

            }
        }
        indicateAddressBookChanged();
    }
    //@@author

    @Override
    public void addToHistory(ReadOnlyTaskManager state) {
        history.push(state);
    }

    //@@author A0164466X
    @Override
    public void updateFilteredListToShowComplete() {
        try {
            updateFilteredPersonList(new PredicateExpression(new TagQualifier(new UniqueTagList(Tag.TAG_COMPLETE))));
        } catch (DuplicateTagException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateFilteredListToShowIncomplete() {
        try {
            updateFilteredPersonList(new PredicateExpression(new TagQualifier(new UniqueTagList(Tag.TAG_INCOMPLETE))));
        } catch (DuplicateTagException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    //=========== Filtered Person List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList() {
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
        boolean satisfies(ReadOnlyTask person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask person);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
  //@@author A0164466X
    private class TagQualifier implements Qualifier {
        private UniqueTagList tags;
        
        TagQualifier(UniqueTagList tags){
            this.tags = tags;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getTags().equals(tags);
        }
        
        //Default toString() method used
        
    }

}
