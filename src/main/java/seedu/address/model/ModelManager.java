package seedu.address.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.validate.ValidationException;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.StorageFilePathChangedEvent;
import seedu.address.commons.events.UserPrefsFilePathChangedEvent;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.util.TaskDeadlineComparator;
import seedu.address.model.util.TaskPriorityComparator;
import seedu.address.model.util.TaskTitleComparator;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.IcsFileStorage;
import seedu.address.storage.XmlAddressBookStorage;

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

    private Comparator<ReadOnlyTask> currentComparator;

    private Expression currentNonFloatingTasksExpression;
    private Expression currentFloatingTasksExpression;
    private Expression currentCompletedTasksExpression;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        initModel(addressBook);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    /**
     * Sets current predicates for task lists to show all tasks.
     */
    private void setCurrentPredicateToShowAllTasks() {
        currentNonFloatingTasksExpression = new PredicateExpression(new TaskIsNotFloatingQualifier());
        currentFloatingTasksExpression = new PredicateExpression(new TaskIsFloatingQualifier());
        currentCompletedTasksExpression = new PredicateExpression(new TaskIsCompleteQualifier());
    }

    //@@author A0144813J
    private void initModel(ReadOnlyAddressBook addressBook) {
        this.addressBookStates = new ArrayList<AddressBook>();
        this.addressBookStates.add(new AddressBook(addressBook));
        this.currentAddressBookStateIndex = 0;
        this.currentAddressBook = new AddressBook(this.addressBookStates.get(this.currentAddressBookStateIndex));
        setCurrentPredicateToShowAllTasks();
        initializeTaskLists();
        updateTaskListPredicate();
        setCurrentComparator(ListCommand.COMPARATOR_NAME_PRIORITY);
    }

    private void initializeTaskLists() {
        this.nonFloatingTasks = new FilteredList<>(this.currentAddressBook.getTaskList());
        this.floatingTasks = new FilteredList<>(this.currentAddressBook.getTaskList());
        this.completedTasks = new FilteredList<>(this.currentAddressBook.getTaskList());
    }

    public void setCurrentComparator(String type) {
        switch (type) {
        case ListCommand.COMPARATOR_NAME_DATE:
            this.currentComparator = new TaskDeadlineComparator();
            break;
        case ListCommand.COMPARATOR_NAME_TITLE:
            this.currentComparator = new TaskTitleComparator();
            break;
        default:
            this.currentComparator = new TaskPriorityComparator();
            break;
        }
        applyCurrentComparatorToTaskList();
    }

    private void applyCurrentComparatorToTaskList() {
        this.currentAddressBook.sortTaskList(currentComparator);
    }

    /**
     * Applies current predicates to the respective task lists.
     */
    private void updateTaskListPredicate() {
        this.nonFloatingTasks.setPredicate(currentNonFloatingTasksExpression::satisfies);
        this.floatingTasks.setPredicate(currentFloatingTasksExpression::satisfies);
        this.completedTasks.setPredicate(currentCompletedTasksExpression::satisfies);
    }

    /**
     * Records the current state of AddressBook to facilitate state transition.
     */
    private void recordCurrentStateOfAddressBook() {
        this.currentAddressBookStateIndex++;
        this.addressBookStates =
                new ArrayList<AddressBook>(this.addressBookStates.subList(0, this.currentAddressBookStateIndex));
        this.addressBookStates.add(new AddressBook(this.currentAddressBook));
    }
    //@@author
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

    //@@author A0144813J
    @Override
    public void indicateAddressBookFilePathChanged(String filePath) throws DataConversionException, IOException {
        raise(new StorageFilePathChangedEvent(filePath));
        AddressBookStorage storage = new XmlAddressBookStorage(filePath);
        Optional<ReadOnlyAddressBook> tempAddressBook = storage.readAddressBook();
        if (tempAddressBook.isPresent()) {
            this.currentAddressBook.resetData(tempAddressBook.get());
            initModel(this.currentAddressBook);
        }
        raise(new AddressBookChangedEvent(this.currentAddressBook));
    }

    @Override
    public void indicateUserPrefsFilePathChanged(String filePath) {
        raise(new UserPrefsFilePathChangedEvent(filePath));
    }

    @Override
    public void saveTasksToIcsFile(String filePath) throws ValidationException, IOException {
        IcsFileStorage.saveDataToFile(filePath, this.currentAddressBook.getTaskList());
    }

    @Override
    public void addTasksFromIcsFile(String filePath)
            throws IOException, ParserException, IllegalValueException {
        List<Task> importedTasks = IcsFileStorage.loadDataFromSaveFile(filePath);
        for (Task task : importedTasks) {
            addTask(task);
        }
    }

    @Override
    public void setAddressBookStateForwards() throws StateLimitReachedException {
        if (this.currentAddressBookStateIndex >= this.addressBookStates.size() - 1) {
            throw new StateLimitReachedException();
        }
        this.currentAddressBookStateIndex++;
        this.currentAddressBook.resetData(this.addressBookStates.get(this.currentAddressBookStateIndex));
        applyCurrentComparatorToTaskList();
        indicateAddressBookChanged();
    }

    @Override
    public void setAddressBookStateBackwards() throws StateLimitReachedException {
        if (this.currentAddressBookStateIndex <= 0) {
            throw new StateLimitReachedException();
        }
        this.currentAddressBookStateIndex--;
        this.currentAddressBook.resetData(this.addressBookStates.get(this.currentAddressBookStateIndex));
        applyCurrentComparatorToTaskList();
        indicateAddressBookChanged();
    }

    @Override
    public void setAddressBookStateToZero() throws StateLimitReachedException {
        if (this.currentAddressBookStateIndex <= 0) {
            throw new StateLimitReachedException();
        }
        while (this.currentAddressBookStateIndex > 0) {
            setAddressBookStateBackwards();
        }
    }

    @Override
    public void setAddressBookStateToFrontier() throws StateLimitReachedException {
        if (this.currentAddressBookStateIndex >= this.addressBookStates.size() - 1) {
            throw new StateLimitReachedException();
        }
        while (this.currentAddressBookStateIndex < this.addressBookStates.size() - 1) {
            setAddressBookStateForwards();
        }
    }
    //@@author
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
        applyCurrentComparatorToTaskList();
    }

    @Override
    public int getDisplayedIndex(ReadOnlyTask task) {
        int index = -1;
        if (task.isFloating()) {
            index = this.floatingTasks.indexOf(task);
        } else {
            index = this.nonFloatingTasks.indexOf(task);
        }
        if (task.isCompleted()) {
            index = this.completedTasks.indexOf(task);
        }
        return index;
    }
    //@@author A0139539R
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
        default:
            addressBookIndex = this.nonFloatingTasks.getSourceIndex(taskListIndex);
            break;
        }

        this.currentAddressBook.updateTask(addressBookIndex, editedTask);
        updateFilteredTaskListToShowAllTasks();
        recordCurrentStateOfAddressBook();
        indicateAddressBookChanged();
        applyCurrentComparatorToTaskList();
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
    public void updateFilteredTaskListToShowFilteredTasks(Set<String> keywords) {
        currentNonFloatingTasksExpression = new PredicateExpression(new NameNonFloatingTaskQualifier(keywords));
        currentFloatingTasksExpression = new PredicateExpression(new NameFloatingTaskQualifier(keywords));
        currentCompletedTasksExpression = new PredicateExpression(new NameCompletedTaskQualifier(keywords));
        updateTaskListPredicate();
    }

    @Override
    public void updateFilteredTaskListToShowFilteredTasks(UniqueTagList tagList) {
        currentNonFloatingTasksExpression = new PredicateExpression(new TagNonFloatingTaskQualifier(tagList));
        currentFloatingTasksExpression = new PredicateExpression(new TagFloatingTaskQualifier(tagList));
        currentCompletedTasksExpression = new PredicateExpression(new TagCompletedTaskQualifier(tagList));
        updateTaskListPredicate();
    }

    @Override
    public void updateFilteredTaskListToShowFilteredTasks(Date date) {
        currentNonFloatingTasksExpression = new PredicateExpression(new DateNonFloatingTaskQualifier(date));
        currentFloatingTasksExpression = new PredicateExpression(new DateFloatingTaskQualifier(date));
        currentCompletedTasksExpression = new PredicateExpression(new DateCompletedTaskQualifier(date));
        updateTaskListPredicate();
    }

    @Override
    public void updateFilteredTaskListToShowAllTasks() {
        setCurrentPredicateToShowAllTasks();
        updateTaskListPredicate();
    }
    //@@author
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
    //@@author A0144813J
    private class TagFloatingTaskQualifier implements Qualifier {
        private UniqueTagList tagList;

        TagFloatingTaskQualifier(UniqueTagList tagList) {
            this.tagList = tagList;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isCoincided = task.isTagListCoincided(tagList);
            boolean isFloatingTask = !task.isCompleted() && task.isFloating();
            return isFloatingTask && isCoincided;
        }
    }

    private class TagNonFloatingTaskQualifier implements Qualifier {
        private UniqueTagList tagList;

        TagNonFloatingTaskQualifier(UniqueTagList tagList) {
            this.tagList = tagList;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isCoincided = task.isTagListCoincided(tagList);
            boolean isNonFloatingTask = !task.isCompleted() && !task.isFloating();
            return isNonFloatingTask && isCoincided;
        }
    }

    private class TagCompletedTaskQualifier implements Qualifier {
        private UniqueTagList tagList;

        TagCompletedTaskQualifier(UniqueTagList tagList) {
            this.tagList = tagList;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isCoincided = task.isTagListCoincided(tagList);
            boolean isCompletedTask = task.isCompleted();
            return isCompletedTask && isCoincided;
        }
    }

    private class DateFloatingTaskQualifier implements Qualifier {
        private Date date;

        DateFloatingTaskQualifier(Date date) {
            this.date = date;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isSameDate = task.getDeadline().isSameDate(date);
            boolean isFloatingTask = !task.isCompleted() && task.isFloating();
            return isFloatingTask && isSameDate;
        }
    }

    private class DateNonFloatingTaskQualifier implements Qualifier {
        private Date date;

        DateNonFloatingTaskQualifier(Date date) {
            this.date = date;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isSameDate = task.getDeadline().isSameDate(date);
            boolean isNonFloatingTask = !task.isCompleted() && !task.isFloating();
            return isNonFloatingTask && isSameDate;
        }
    }

    private class DateCompletedTaskQualifier implements Qualifier {
        private Date date;

        DateCompletedTaskQualifier(Date date) {
            this.date = date;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean isSameDate = task.getDeadline().isSameDate(date);
            boolean isCompletedTask = task.isCompleted();
            return isCompletedTask && isSameDate;
        }
    }

    //@@author
    //@@author A0139539R
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
    //@@author
    //@@author A0144813J
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
    //@@author
}
