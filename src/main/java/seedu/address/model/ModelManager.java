package seedu.address.model;

//import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyTask> nonFloatingTasks;
    private final FilteredList<ReadOnlyTask> floatingTasks;
    //private final FilteredList<ReadOnlyTask> completedTasks;
    private static final int MATCHING_INDEX = 35;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        nonFloatingTasks = new FilteredList<>(this.addressBook.getTaskList());
        nonFloatingTasks.setPredicate(new PredicateExpression(new DateNotFloatingQualifier())::satisfies);
        floatingTasks = new FilteredList<>(this.addressBook.getTaskList());
        floatingTasks.setPredicate(new PredicateExpression(new DateFloatingQualifier())::satisfies);
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
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(String targetList, int taskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        int addressBookIndex = nonFloatingTasks.getSourceIndex(taskListIndex);
        if (targetList.equals("floating")) {
            addressBookIndex = floatingTasks.getSourceIndex(taskListIndex);
        }
        addressBook.updateTask(addressBookIndex, editedTask);
        indicateAddressBookChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getNonFloatingTaskList() {
        return new UnmodifiableObservableList<>(nonFloatingTasks);
    }

    public UnmodifiableObservableList<ReadOnlyTask> getFloatingTaskList() {
        return new UnmodifiableObservableList<>(floatingTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        //filteredTasks.setPredicate(new PredicateExpression(new DateFloatingQualifier())::satisfies);
        nonFloatingTasks.setPredicate(new PredicateExpression(new DateNotFloatingQualifier())::satisfies);
        //filteredTasks.setPredicate(null);
    }

    public void updateFilteredListToShowAllFloatingTasks() {
        floatingTasks.setPredicate(new PredicateExpression(new DateFloatingQualifier())::satisfies);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        nonFloatingTasks.setPredicate(expression::satisfies);
    }

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

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> fuzzyFind(task.getTitle().title.toLowerCase(), keyword))
                    .findAny()
                    .isPresent();
        }

        public boolean fuzzyFind(String title, String keyword) {
            return FuzzySearch.ratio(title, keyword) > MATCHING_INDEX;
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private class DateFloatingQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getDeadline().toString().equals("floating");
        }

    }

    private class DateNotFloatingQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return !task.getDeadline().toString().equals("floating");
        }

    }
}
