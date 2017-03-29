package seedu.taskit.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.taskit.commons.core.ComponentManager;
import seedu.taskit.commons.core.LogsCenter;
import seedu.taskit.commons.core.UnmodifiableObservableList;
import seedu.taskit.commons.events.model.AddressBookChangedEvent;
import seedu.taskit.commons.util.CollectionUtil;
import seedu.taskit.commons.util.StringUtil;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.UniqueTaskList;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int addressBookIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        addressBook.updateTask(addressBookIndex, editedTask);
        indicateAddressBookChanged();
    }

//=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }
    
    //@author A0141872E
    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public int updateFilteredTaskList(String parameter) {
        updateFilteredTaskList(new PredicateExpression(new ParameterQualifier(parameter)));
        return filteredTasks.size();
    }
    //@author

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    //@@author A0097141H
    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        			// might not need this first boolean
        	return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.toStringTitleAndTagList(), keyword.toLowerCase()))
                    .findAny()
                    .isPresent() |                    
                    nameKeyWords.stream()
                    .filter(keyword -> task.toStringTitleAndTagList().contains(keyword.toLowerCase()))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    //@@author
    
    private class ParameterQualifier implements Qualifier {
        private String parameter;
        
        ParameterQualifier(String parameter) {
            this.parameter=parameter;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            switch (parameter){
                case "done":
                    return task.isDone();
                
                case "undone":
                    return !task.isDone();
                    
                case "overdue":
                    return task.isOverdue() && !task.isDone();
                
                case "today":
                    return !task.isDone() && task.getEnd().isDateEqualCurrentDate();
            
                default:
                    return false;
            }
        }
        
        @Override
        public String toString() {
            return "parameter=" + String.join(", ", parameter);
        }
        
    }

}
