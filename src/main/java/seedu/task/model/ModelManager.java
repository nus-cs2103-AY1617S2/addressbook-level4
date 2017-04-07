package seedu.task.model;

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
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.StorageFilePathChangedEvent;
import seedu.task.commons.events.UserPrefsFilePathChangedEvent;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.logic.commands.ListCommand;
import seedu.task.model.commandmap.CommandMap.BaseCommandNotAllowedAsAliasException;
import seedu.task.model.commandmap.CommandMap.OriginalCommandNotFoundException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.task.model.util.TaskDeadlineComparator;
import seedu.task.model.util.TaskPriorityComparator;
import seedu.task.model.util.TaskTitleComparator;
import seedu.task.storage.IcsFileStorage;
import seedu.task.storage.TaskManagerStorage;
import seedu.task.storage.XmlTaskManagerStorage;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private List<TaskManager> taskManagerStates;
    private TaskManager currentTaskManager;
    private int currentTaskManagerStateIndex;
    private static final int MATCHING_INDEX = 35;

    public FilteredList<ReadOnlyTask> nonFloatingTasks;
    public FilteredList<ReadOnlyTask> floatingTasks;
    public FilteredList<ReadOnlyTask> completedTasks;

    private Comparator<ReadOnlyTask> currentComparator;

    private Expression currentNonFloatingTasksExpression;
    private Expression currentFloatingTasksExpression;
    private Expression currentCompletedTasksExpression;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        initModel(taskManager);
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
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
    private void initModel(ReadOnlyTaskManager taskManager) {
        this.currentTaskManager = new TaskManager();
        initTaskManager(taskManager);
        setCurrentPredicateToShowAllTasks();
        initializeTaskLists();
        updateTaskListPredicate();
        setCurrentComparator(ListCommand.COMPARATOR_NAME_PRIORITY);
    }

    private void initTaskManager(ReadOnlyTaskManager taskManager) {
        this.currentTaskManager.resetData(taskManager);
        this.taskManagerStates = new ArrayList<TaskManager>();
        this.taskManagerStates.add(new TaskManager(taskManager));
        this.currentTaskManagerStateIndex = 0;
    }

    private void initializeTaskLists() {
        this.nonFloatingTasks = new FilteredList<>(this.currentTaskManager.getTaskList());
        this.floatingTasks = new FilteredList<>(this.currentTaskManager.getTaskList());
        this.completedTasks = new FilteredList<>(this.currentTaskManager.getTaskList());
    }

    @Override
    public String translateCommand(String original) {
        return this.currentTaskManager.translateCommand(original);
    }

    @Override
    public void addCommandAlias(String alias, String original) throws OriginalCommandNotFoundException,
            BaseCommandNotAllowedAsAliasException {
        this.currentTaskManager.addCommandAlias(alias, original);
        recordCurrentStateOfTaskManager();
        indicateTaskManagerChanged();
    }

    @Override
    public String getCommandMapString() {
        return this.currentTaskManager.getCommandMapString();
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
        this.currentTaskManager.sortTaskList(currentComparator);
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
     * Records the current state of TaskManager to facilitate state transition.
     */
    private void recordCurrentStateOfTaskManager() {
        this.currentTaskManagerStateIndex++;
        this.taskManagerStates =
                new ArrayList<TaskManager>(this.taskManagerStates.subList(0, this.currentTaskManagerStateIndex));
        this.taskManagerStates.add(new TaskManager(this.currentTaskManager));
    }
    //@@author
    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        this.currentTaskManager.resetData(newData);
        recordCurrentStateOfTaskManager();
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return this.currentTaskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(this.currentTaskManager));
    }

    //@@author A0144813J
    @Override
    public void indicateTaskManagerFilePathChanged(String filePath) throws DataConversionException, IOException {
        TaskManagerStorage storage = new XmlTaskManagerStorage(filePath);
        Optional<ReadOnlyTaskManager> tempTaskManager = storage.readTaskManager();
        // Raises StorageFilePathChangedEvent if no Exception is thrown.
        raise(new StorageFilePathChangedEvent(filePath));
        if (tempTaskManager.isPresent()) {
            initTaskManager(tempTaskManager.get());
            setCurrentPredicateToShowAllTasks();
            updateTaskListPredicate();
            setCurrentComparator(ListCommand.COMPARATOR_NAME_PRIORITY);
        } else {
            // Creates new storage file if not exists.
            raise(new TaskManagerChangedEvent(this.currentTaskManager));
        }
    }

    @Override
    public void indicateUserPrefsFilePathChanged(String filePath) {
        raise(new UserPrefsFilePathChangedEvent(filePath));
    }

    @Override
    public void saveTasksToIcsFile(String filePath) throws ValidationException, IOException {
        IcsFileStorage.saveDataToFile(filePath, this.currentTaskManager.getTaskList());
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
    public void setTaskManagerStateForwards() throws StateLimitReachedException {
        if (this.currentTaskManagerStateIndex >= this.taskManagerStates.size() - 1) {
            throw new StateLimitReachedException();
        }
        this.currentTaskManagerStateIndex++;
        this.currentTaskManager.resetData(this.taskManagerStates.get(this.currentTaskManagerStateIndex));
        applyCurrentComparatorToTaskList();
        indicateTaskManagerChanged();
    }

    @Override
    public void setTaskManagerStateBackwards() throws StateLimitReachedException {
        if (this.currentTaskManagerStateIndex <= 0) {
            throw new StateLimitReachedException();
        }
        this.currentTaskManagerStateIndex--;
        this.currentTaskManager.resetData(this.taskManagerStates.get(this.currentTaskManagerStateIndex));
        applyCurrentComparatorToTaskList();
        indicateTaskManagerChanged();
    }

    @Override
    public void setTaskManagerStateToZero() throws StateLimitReachedException {
        if (this.currentTaskManagerStateIndex <= 0) {
            throw new StateLimitReachedException();
        }
        while (this.currentTaskManagerStateIndex > 0) {
            setTaskManagerStateBackwards();
        }
    }

    @Override
    public void setTaskManagerStateToFrontier() throws StateLimitReachedException {
        if (this.currentTaskManagerStateIndex >= this.taskManagerStates.size() - 1) {
            throw new StateLimitReachedException();
        }
        while (this.currentTaskManagerStateIndex < this.taskManagerStates.size() - 1) {
            setTaskManagerStateForwards();
        }
    }
    //@@author
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        this.currentTaskManager.removeTask(target);
        recordCurrentStateOfTaskManager();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        this.currentTaskManager.addTask(task);
        recordCurrentStateOfTaskManager();
        indicateTaskManagerChanged();
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
        int taskManagerIndex = 0;
        switch (targetList) {
        case Task.TASK_NAME_FLOATING:
            taskManagerIndex = this.floatingTasks.getSourceIndex(taskListIndex);
            break;
        case Task.TASK_NAME_COMPLETED:
            taskManagerIndex = this.completedTasks.getSourceIndex(taskListIndex);
            break;
        default:
            taskManagerIndex = this.nonFloatingTasks.getSourceIndex(taskListIndex);
            break;
        }

        this.currentTaskManager.updateTask(taskManagerIndex, editedTask);
        updateFilteredTaskListToShowAllTasks();
        recordCurrentStateOfTaskManager();
        indicateTaskManagerChanged();
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
            boolean isTagMatching = false;
            UniqueTagList tagList = task.getTags();
            boolean tempIsTagMatching;
            for (Tag tag : tagList.internalList) {
                tempIsTagMatching = nameKeyWords.stream()
                        .filter(keyword -> fuzzyFind(tag.tagName.toLowerCase(), keyword.toLowerCase()))
                        .findAny()
                        .isPresent();
                isTagMatching |= tempIsTagMatching;
                if (tempIsTagMatching) {
                    break;
                }
            }
            return isTagMatching || nameKeyWords.stream()
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
