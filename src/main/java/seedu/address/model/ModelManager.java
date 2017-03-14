package seedu.address.model;

import java.util.ArrayList;
import java.util.List;
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

    private List<AddressBook> addressBookStates;
    private AddressBook currentAddressBook;
    private int currentAddressBookStateIndex;
    private static final int MATCHING_INDEX = 35;

    public FilteredList<ReadOnlyTask> nonFloatingTasks;
    public FilteredList<ReadOnlyTask> floatingTasks;
    public FilteredList<ReadOnlyTask> completedTasks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBookStates = new ArrayList<AddressBook>();
        this.addressBookStates.add(new AddressBook(addressBook));
        this.currentAddressBookStateIndex = 0;
        this.currentAddressBook = new AddressBook(this.addressBookStates.get(this.currentAddressBookStateIndex));

        setAddressBookState();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public void setAddressBookState() {
        this.nonFloatingTasks = new FilteredList<>(this.currentAddressBook.getTaskList());
        this.floatingTasks = new FilteredList<>(this.currentAddressBook.getTaskList());
        this.completedTasks = new FilteredList<>(this.currentAddressBook.getTaskList());
        updateFilteredListToShowAllNonFloating();
        updateFilteredListToShowAllFloatingTasks();
        updateFilteredListToShowAllCompletedTasks();
    }

    /**
     * Records the current state of AddressBook to facilitate state transition.
     */
    private void recordCurrentStateOfAddressBook() {
        this.currentAddressBookStateIndex++;
        this.addressBookStates = new ArrayList<AddressBook>(this.addressBookStates.subList(0, this.currentAddressBookStateIndex));
        this.addressBookStates.add(new AddressBook(this.currentAddressBook));
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        this.currentAddressBook.resetData(newData);
        recordCurrentStateOfAddressBook();
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return this.currentAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(this.currentAddressBook));
    }

    /** Raises an event to indicate the model and its state have changed */
    private void indicateAddressBookStateChanged() {
        AddressBookChangedEvent abce = new AddressBookChangedEvent(this.currentAddressBook);
        abce.setFloatingTasks(getFloatingTaskList());
        abce.setNonFloatingTasks(getNonFloatingTaskList());
        abce.setCompletedTasks(getCompletedTaskList());
        raise(abce);
    }

    @Override
    public void setAddressBookStateForwards() throws StateLimitReachedException {
        if (this.currentAddressBookStateIndex >= this.addressBookStates.size() - 1) {
            throw new StateLimitReachedException();
        }
        this.currentAddressBookStateIndex++;
        this.currentAddressBook = new AddressBook(this.addressBookStates.get(this.currentAddressBookStateIndex));
        setAddressBookState();
        indicateAddressBookStateChanged();
    }

    @Override
    public void setAddressBookStateBackwards() throws StateLimitReachedException {
        if (this.currentAddressBookStateIndex <= 0) {
            throw new StateLimitReachedException();
        }
        this.currentAddressBookStateIndex--;
        this.currentAddressBook = new AddressBook(this.addressBookStates.get(this.currentAddressBookStateIndex));
        setAddressBookState();
        indicateAddressBookStateChanged();
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        this.currentAddressBook.removeTask(target);
        recordCurrentStateOfAddressBook();
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        this.currentAddressBook.addTask(task);
        recordCurrentStateOfAddressBook();
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(String targetList, int taskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;
        int addressBookIndex = 0;
        switch (targetList) {
        case Task.TASK_NAME_FLOATING:
            addressBookIndex = this.floatingTasks.getSourceIndex(taskListIndex);
            break;
        case Task.TASK_NAME_COMPLETED:
            addressBookIndex = this.completedTasks.getSourceIndex(taskListIndex);
            break;
        case Task.TASK_NAME_NON_FLOATING:
            addressBookIndex = this.nonFloatingTasks.getSourceIndex(taskListIndex);
            break;
        default:
            addressBookIndex = this.nonFloatingTasks.getSourceIndex(taskListIndex);
            break;
        }

        this.currentAddressBook.updateTask(addressBookIndex, editedTask);
        recordCurrentStateOfAddressBook();
        indicateAddressBookChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getNonFloatingTaskList() {
        return new UnmodifiableObservableList<>(this.nonFloatingTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFloatingTaskList() {
        return new UnmodifiableObservableList<>(this.floatingTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getCompletedTaskList() {
        return new UnmodifiableObservableList<>(this.completedTasks);
    }

    @Override
    public void updateFilteredListToShowAllNonFloating() {
        this.nonFloatingTasks.setPredicate(new PredicateExpression(new TaskIsNotFloatingQualifier())::satisfies);
    }

    @Override
    public void updateFilteredListToShowAllFloatingTasks() {
        this.floatingTasks.setPredicate(new PredicateExpression(new TaskIsFloatingQualifier())::satisfies);
    }

    @Override
    public void updateFilteredListToShowAllCompletedTasks() {
        this.completedTasks.setPredicate(new PredicateExpression(new TaskIsCompleteQualifier())::satisfies);
    }

    @Override
    public void updateFilteredListToShowFilteredNonFloatingTasks(Set<String> keywords) {
        this.nonFloatingTasks.setPredicate(new PredicateExpression(new NameNonFloatingTaskQualifier(keywords))::satisfies);
    }

    @Override
    public void updateFilteredListToShowFilteredFloatingTasks(Set<String> keywords) {
        this.floatingTasks.setPredicate(new PredicateExpression(new NameFloatingTaskQualifier(keywords))::satisfies);
    }

    @Override
    public void updateFilteredListToShowFilteredCompletedTasks(Set<String> keywords) {
        this.completedTasks.setPredicate(new PredicateExpression(new NameCompletedTaskQualifier(keywords))::satisfies);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
    	updateFilteredListToShowFilteredNonFloatingTasks(keywords);
    	updateFilteredListToShowFilteredFloatingTasks(keywords);
    	updateFilteredListToShowFilteredCompletedTasks(keywords);
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

    private class NameFloatingTaskQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private FuzzyFinder fuzzyFinder;

        NameFloatingTaskQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
            this.fuzzyFinder = new FuzzyFinder();
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isKeywordPresent = fuzzyFinder.check(task, nameKeyWords);
            boolean isFloatingTask = !task.isCompleted() && task.isFloating();
            return isFloatingTask && isKeywordPresent;
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private class NameNonFloatingTaskQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private FuzzyFinder fuzzyFinder;

        NameNonFloatingTaskQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
            this.fuzzyFinder = new FuzzyFinder();
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isKeywordPresent = fuzzyFinder.check(task, nameKeyWords);
            boolean isNonFloatingTask = !task.isCompleted() && !task.isFloating();
            return isNonFloatingTask && isKeywordPresent;
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private class NameCompletedTaskQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private FuzzyFinder fuzzyFinder;

        NameCompletedTaskQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
            this.fuzzyFinder = new FuzzyFinder();
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isKeywordPresent = fuzzyFinder.check(task, nameKeyWords);
            boolean isCompletedTask = task.isCompleted();
            return isCompletedTask && isKeywordPresent;
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private class FuzzyFinder {

        public boolean check(ReadOnlyTask task, Set<String> nameKeyWords) {
            return nameKeyWords.stream()
                    .filter(keyword -> fuzzyFind(task.getTitle().title.toLowerCase(), keyword.toLowerCase()))
                    .findAny()
                    .isPresent();
        }

        public boolean fuzzyFind(String title, String keyword) {
            return FuzzySearch.ratio(title, keyword) > MATCHING_INDEX;
        }
    }

    private class TaskIsFloatingQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return !task.isCompleted() && task.isFloating();
        }

    }

    private class TaskIsNotFloatingQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return !task.isCompleted() && !task.isFloating();
        }

    }

    private class TaskIsCompleteQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.isCompleted();
        }

    }
}
