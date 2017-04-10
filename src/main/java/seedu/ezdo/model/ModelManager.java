package seedu.ezdo.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.ezdo.commons.core.ComponentManager;
import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.events.model.IsSortedAscendingChangedEvent;
import seedu.ezdo.commons.events.model.SortCriteriaChangedEvent;
import seedu.ezdo.commons.exceptions.DateException;
import seedu.ezdo.commons.exceptions.RecurException;
import seedu.ezdo.commons.util.CollectionUtil;
import seedu.ezdo.commons.util.DateUtil;
import seedu.ezdo.commons.util.RecurUtil;
import seedu.ezdo.commons.util.SearchParameters;
import seedu.ezdo.commons.util.StringUtil;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.TaskDate;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;
import seedu.ezdo.model.todo.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the ezDo data. All changes to any model
 * should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    public static final int STACK_CAPACITY = 5;

    private final EzDo ezDo;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final UserPrefs userPrefs;
    // @@author A0139248X
    private SortCriteria currentSortCriteria;
    private Boolean currentIsSortedAscending;

    private FixedStack<ReadOnlyEzDo> undoStack;
    private FixedStack<ReadOnlyEzDo> redoStack;

    /**
     * Initializes a ModelManager with the given ezDo and userPrefs.
     */
    public ModelManager(ReadOnlyEzDo ezDo, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(ezDo, userPrefs);

        logger.fine("Initializing with ezDo: " + ezDo + " and user prefs " + userPrefs);

        this.ezDo = new EzDo(ezDo);
        this.userPrefs = userPrefs;
        filteredTasks = new FilteredList<>(this.ezDo.getTaskList());
        initSortPrefs();
        initStacks();
        updateFilteredListToShowAll();
    }

    private void initSortPrefs() {
        currentSortCriteria = userPrefs.getSortCriteria();
        currentIsSortedAscending = userPrefs.getIsSortedAscending();
    }

    private void initStacks() {
        undoStack = new FixedStack<ReadOnlyEzDo>(STACK_CAPACITY);
        redoStack = new FixedStack<ReadOnlyEzDo>(STACK_CAPACITY);
    }

    public ModelManager() {
        this(new EzDo(), new UserPrefs());
    }

    /**
     * Resets ezDo.
     */
    @Override
    public void resetData(ReadOnlyEzDo newData) {
        updateStacks();
        ezDo.resetData(newData);
        indicateEzDoChanged();
    }

    // @@author
    @Override
    public ReadOnlyEzDo getEzDo() {
        return ezDo;
    }

    @Override
    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateEzDoChanged() {
        raise(new EzDoChangedEvent(ezDo));
    }

    // @@author A0139248X
    /**
     * Deletes the tasks in {@code tasksToKill}.
     *
     * @throws TaskNotFoundException
     *             if a task is not found in ezDo
     */
    @Override
    public synchronized void killTasks(ArrayList<ReadOnlyTask> tasksToKill) throws TaskNotFoundException {
        updateStacks();
        ezDo.removeTasks(tasksToKill);
        updateFilteredListToShowAll();
        indicateEzDoChanged();
    }

    /**
     * Adds a task to ezDo.
     *
     * @throws DuplicateTaskException
     *             if the task to be added already exists
     * @throws DateException
     *             if the dates are invalid (start date after due date)
     */
    @Override
    public synchronized void addTask(Task task)
            throws UniqueTaskList.DuplicateTaskException, DateException, RecurException {
        checkTaskDate(task);
        checkRecur(task);
        updateStacks();
        ezDo.addTask(task);
        ezDo.sortTasks(currentSortCriteria, currentIsSortedAscending);
        updateFilteredListToShowAll();
        indicateEzDoChanged();
    }

    /**
     * Toggles the done status of the tasks in {@code toggleTasks}
     */
    @Override
    public synchronized boolean toggleTasksDone(ArrayList<Task> toggleTasks) {
        updateStacks();
        ezDo.toggleTasksDone(toggleTasks);
        final boolean isSetToDone = toggleTasks.get(0).getDone();
        if (isSetToDone) {
            updateFilteredListToShowAll();
        } else {
            updateFilteredDoneList();
        }
        ezDo.sortTasks(currentSortCriteria, currentIsSortedAscending);
        indicateEzDoChanged();
        return isSetToDone;
    }

    // @@author A0139177W
    /**
     * Toggles the select status of the tasks in {@code toggleSelect}
     */
    @Override
    public boolean toggleTasksSelect(ArrayList<Task> tasksToToggle) {
        int firstElement = 0;
        ezDo.toggleTasksSelect(tasksToToggle);
        final boolean isSetToSelect = tasksToToggle.get(firstElement).getStarted();
        updateFilteredListToShowAll();
        ezDo.sortTasks(currentSortCriteria, currentIsSortedAscending);
        indicateEzDoChanged();
        return isSetToSelect;
    }
    // @@author

    /**
     * Updates an existing task in ezDo.
     *
     * @throws DuplicateTaskException
     *             if the edited task is a duplicate
     * @throws DateException
     *             if the dates are invalid(start date after due date)
     */
    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException, DateException, RecurException {
        assert editedTask != null;
        checkTaskDate(editedTask);
        checkRecur(editedTask);
        updateStacks();
        int ezDoIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        ezDo.updateTask(ezDoIndex, editedTask);
        ezDo.sortTasks(currentSortCriteria, currentIsSortedAscending);
        indicateEzDoChanged();
    }

    /**
     * Undo ezDo to the previous state.
     *
     * @throws EmptyStackException
     *             if there is no command to undo.
     */
    @Override
    public void undo() throws EmptyStackException {
        ReadOnlyEzDo currentState = new EzDo(this.getEzDo());
        ezDo.resetData(undoStack.pop());
        redoStack.push(currentState);
        updateFilteredListToShowAll();
        indicateEzDoChanged();
    }

    /**
     * Redo ezDo to the previous state.
     *
     * @throws EmptyStackException
     *             if there is no undone command to redo
     */
    @Override
    public void redo() throws EmptyStackException {
        ReadOnlyEzDo currentState = new EzDo(this.getEzDo());
        ezDo.resetData(redoStack.pop());
        undoStack.push(currentState);
        updateFilteredListToShowAll();
        indicateEzDoChanged();
    }

    /**
     * Update the undo/redo stacks.
     */
    @Override
    public void updateStacks() {
        ReadOnlyEzDo prevState = new EzDo(this.getEzDo());
        undoStack.push(prevState);
        redoStack.clear();
    }

    /**
     * Checks if a task's dates are valid.
     *
     * @throws DateException
     *             if the start date is after the due date
     */
    @Override
    public void checkTaskDate(ReadOnlyTask task) throws DateException {
        assert task != null;
        try {
            if (!DateUtil.isTaskDateValid(task)) {
                throw new DateException("Start date after due date!");
            }
        } catch (ParseException pe) {
            logger.info("Parse exception while checking if task date valid");
            throw new DateException("Error parsing dates!");
        }
    }

    // @@author
    // =========== Filtered Task List Accessors
    // =============================================================
    // @@author A0141010L
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredTaskList(SearchParameters searchParameters) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(searchParameters)));
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        updateFilteredTaskList(new PredicateExpression(new NotDoneQualifier()));
    }

    @Override
    public void updateFilteredDoneList() {
        updateFilteredTaskList(new PredicateExpression(new DoneQualifier()));
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
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
    }

    interface Qualifier {
        /**
         * returns true if a task agrees with a given {@code Qualifier}
         */
        boolean run(ReadOnlyTask task);
    }

    /*
     * Qualifier to check if a task is done
     */
    private class DoneQualifier implements Qualifier {

        DoneQualifier() {

        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getDone();
        }
    }

    /*
     * Qualifier to check if a task is not done
     */
    private class NotDoneQualifier implements Qualifier {

        NotDoneQualifier() {

        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return !task.getDone();
        }
    }

    private class NameQualifier implements Qualifier {

        public static final String EMPTY_STRING = "";
        private Set<String> nameKeyWords;
        private Optional<Priority> priority;
        private Optional<TaskDate> startDate;
        private Optional<TaskDate> dueDate;
        private Optional<Recur> recur;
        private Set<String> tags;
        private boolean startBefore;
        private boolean dueBefore;
        private boolean startAfter;
        private boolean dueAfter;

        NameQualifier(SearchParameters searchParameters) {

            nameKeyWords = searchParameters.getNames();
            priority = searchParameters.getPriority();
            startDate = searchParameters.getStartDate();
            dueDate = searchParameters.getDueDate();
            recur = searchParameters.getRecur();
            tags = searchParameters.getTags();
            startBefore = searchParameters.getStartBefore();
            dueBefore = searchParameters.getdueBefore();
            startAfter = searchParameters.getStartAfter();
            dueAfter = searchParameters.getDueAfter();

        }

        @Override
        public boolean run(ReadOnlyTask task) {

            Set<String> taskTagStringSet = convertToTagStringSet(task.getTags().toSet());
            boolean isNameEqual = nameKeyWords.contains(EMPTY_STRING) || nameKeyWords.stream()
                    .allMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword));
            boolean isPriorityEqual = comparePriority(task.getPriority());
            boolean isStartDateQualified = compareStart(task);
            boolean isDueDateQualified = compareDue(task);
            boolean isRecurQualified = compareRecur(task.getRecur());
            boolean areTagsEqual = (taskTagStringSet.containsAll(tags));

            boolean isQualified = isNameEqual && !task.getDone() && isPriorityEqual && isStartDateQualified
                    && isDueDateQualified && isRecurQualified && areTagsEqual;

            return isQualified;

        }

        /**
         * convert a given {@code Set} of {@code Tags} to a {@code Set} of
         * {@code String}
         */
        private Set<String> convertToTagStringSet(Set<Tag> tags) {
            Tag[] tagArray = tags.toArray(new Tag[tags.size()]);
            Set<String> tagSet = new HashSet<String>();

            for (int i = 0; i < tags.size(); i++) {
                tagSet.add((tagArray[i]).tagName);
            }

            return tagSet;
        }

        /**
         * returns true if the task's {@code StartDate} qualifies a given
         * {@code StartDate}, before and after boolean status
         */
        private boolean compareStart(ReadOnlyTask task) {
            boolean isQualified = (((!startBefore && !startAfter) && compareStartDate(task.getStartDate()))
                    || (startBefore && compareBeforeStart(task.getStartDate()))
                    || (startAfter && compareAfterStart(task.getStartDate())));
            return isQualified;
        }

        /**
         * returns true if the task's {@code DueDate} qualifies a given
         * {@code DueDate}, before and after boolean status
         */
        private boolean compareDue(ReadOnlyTask task) {
            boolean isQualified = (((!dueBefore && !dueAfter) && compareDueDate(task.getDueDate()))
                    || (dueBefore && compareBeforeDue(task.getDueDate()))
                    || (dueAfter && compareAfterDue(task.getDueDate())));
            return isQualified;
        }

        /**
         * returns true if task's {@code Priority} equals given {@code Priority}
         */
        private boolean comparePriority(Priority taskPriority) {

            String taskPriorityString = taskPriority.toString();
            boolean priorityExist = (taskPriorityString.length() != 0);

            boolean isEqual = (!priority.isPresent()
                    || (EMPTY_STRING.equals(priority.get().toString()) && priorityExist)
                    || (priorityExist && taskPriorityString.equals(priority.get().toString())));

            return isEqual;
        }

        /**
         * returns true if task's {@code Recur} equals given {@code Recur}
         */
        private boolean compareRecur(Recur taskRecur) {
            assert taskRecur != null;
            String taskRecurString = taskRecur.toString();
            boolean recurExist = (taskRecurString.length() != 0);

            boolean isEqual = (!recur.isPresent() || (EMPTY_STRING.equals(recur.get().toString()) && recurExist)
                    || (recurExist && taskRecurString.equals(recur.get().toString())));

            return isEqual;
        }

        /**
         * returns true if task's {@code StartDate} equals given {@code DueDate}
         */
        private boolean compareStartDate(TaskDate taskStartDate) {
            assert taskStartDate != null;
            String taskStartDateString = taskStartDate.toString();
            boolean taskStartDateExist = (taskStartDateString.length() != 0);
            int dateLength = 10;

            boolean isStartEqual = (!startDate.isPresent()
                    || (EMPTY_STRING.equals(startDate.get().toString()) && taskStartDateExist)
                    || (taskStartDateExist && taskStartDateString.substring(0, dateLength)
                            .equals(startDate.get().toString().substring(0, dateLength))));

            return isStartEqual;
        }

        /**
         * returns true if task's {@code DueDate} equals given {@code DueDate}
         */
        private boolean compareDueDate(TaskDate taskDueDate) {
            assert taskDueDate != null;
            String taskDueDateString = taskDueDate.toString();
            boolean taskDueDateExist = (taskDueDateString.length() != 0);
            int dateLength = 10;

            boolean isDueEqual = (!dueDate.isPresent()
                    || (EMPTY_STRING.equals(dueDate.get().toString()) && taskDueDateExist)
                    || (taskDueDateExist && taskDueDateString.substring(0, dateLength)
                            .equals(dueDate.get().toString().substring(0, dateLength))));

            return isDueEqual;
        }

        /**
         * returns true if task's {@code StartDate} comes before given
         * {@code StartDate} returns true if task's {@code StartDate} comes
         * before given {@code StartDate}
         *
         */
        private boolean compareBeforeStart(TaskDate taskStartDate) {
            assert taskStartDate != null;
            String taskStartDateString = taskStartDate.toString();
            boolean taskStartDateExist = (taskStartDateString.length() != 0);

            boolean isBefore = (!startDate.isPresent()
                    || (EMPTY_STRING.equals(startDate.get().toString()) && taskStartDateExist)
                    || (taskStartDateExist && comesBefore(startDate.get().toString(), taskStartDateString)));

            return isBefore;
        }

        /**
         * returns true if task's {@code DueDate} comes before given
         * {@code DueDate}
         */
        private boolean compareBeforeDue(TaskDate taskDueDate) {
            assert taskDueDate != null;
            String taskDueDateString = taskDueDate.toString();
            boolean taskDueDateExist = (taskDueDateString.length() != 0);

            boolean isBefore = (!dueDate.isPresent()
                    || (EMPTY_STRING.equals(dueDate.get().toString()) && taskDueDateExist)
                    || (taskDueDateExist && comesBefore(dueDate.get().toString(), taskDueDateString)));

            return isBefore;
        }

        /**
         * returns true if a task's {@code StartDate} comes after given
         * {@code StartDate}
         */
        private boolean compareAfterStart(TaskDate taskStartDate) {
            assert taskStartDate != null;
            String taskStartDateString = taskStartDate.toString();
            boolean taskStartDateExist = (taskStartDateString.length() != 0);

            boolean isAfter = (!startDate.isPresent()
                    || (EMPTY_STRING.equals(startDate.get().toString()) && taskStartDateExist)
                    || (taskStartDateExist && comesBefore(taskStartDateString, startDate.get().toString())));

            return isAfter;
        }

        /**
         * returns true if task's {@code DueDate} comes after given
         * {@code DueDate}
         *
         */
        private boolean compareAfterDue(TaskDate taskDueDate) {
            assert taskDueDate != null;
            String taskDueDateString = taskDueDate.toString();
            boolean taskDueDateExist = (taskDueDateString.length() != 0);

            boolean isAfter = (!dueDate.isPresent()
                    || (EMPTY_STRING.equals(dueDate.get().toString()) && taskDueDateExist)
                    || (taskDueDateExist && comesBefore(taskDueDateString, dueDate.get().toString())));

            return isAfter;
        }

        /**
         * returns true if {@code givenDate2} comes before {@code givenDate1}
         */
        private boolean comesBefore(String givenDate1, String givenDate2) {
            assert givenDate1 != null;
            assert givenDate2 != null;
            // slice a given date format DD/MM/YYYY MM:SS into DD,MM,YYYY
            // separate strings
            int givenDD = Integer.parseInt(givenDate1.substring(0, 2));
            int givenMM = Integer.parseInt(givenDate1.substring(3, 5));
            int givenYYYY = Integer.parseInt(givenDate1.substring(6, 10));

            int taskDD = Integer.parseInt(givenDate2.substring(0, 2));
            int taskMM = Integer.parseInt(givenDate2.substring(3, 5));
            int taskYYYY = Integer.parseInt(givenDate2.substring(6, 10));

            boolean isBefore = (taskYYYY < givenYYYY) || ((taskYYYY == givenYYYY) && (taskMM < givenMM))
                    || ((taskYYYY == givenYYYY) && (taskMM == givenMM) && (taskDD <= givenDD));

            return isBefore;

        }

    }

    // @@author A0138907W
    /**
     * Sorts the task in ezDo by the given sort criteria.
     *
     * @param sortCriteria
     *            The field to sort by.
     * @param isSortedAscending
     *            If true, sorts in ascending order. Otherwise, sorts in
     *            descending order.
     */
    @Override
    public void sortTasks(SortCriteria sortCriteria, Boolean isSortedAscending) {
        if (!this.currentSortCriteria.equals(sortCriteria)) {
            this.currentSortCriteria = sortCriteria;
            indicateSortCriteriaChanged();
        }
        if (!this.currentIsSortedAscending.equals(isSortedAscending)) {
            this.currentIsSortedAscending = isSortedAscending;
            indicateIsSortedAscendingChanged();
        }
        ezDo.sortTasks(sortCriteria, isSortedAscending);
        indicateEzDoChanged();
    }

    // @@author
    // @@author A0139248X
    /**
     * Raises a {@code SortCriteriaChangedEvent}.
     */

    public void indicateSortCriteriaChanged() {
        raise(new SortCriteriaChangedEvent(currentSortCriteria));
    }

    // @@author A0138907W
    /**
     * Raises a {@code IsSortedAscendingChangedEvent}.
     */
    public void indicateIsSortedAscendingChanged() {
        raise(new IsSortedAscendingChangedEvent(currentIsSortedAscending));
    }

    //@@author A0139177W
    /**
     * Throws a RecurException if a floating task is found with a recurring status.
     * @param   readOnlyTask
     * @throws  RecurException  A floating task is found with recurring status.
     */
    public void checkRecur(ReadOnlyTask readOnlyTask) throws RecurException {
        String recurErrorMessage = "To set a recur status, both start and due dates cannot be empty.";
        assert readOnlyTask != null;
        if (!RecurUtil.isRecurValid(readOnlyTask)) {
            throw new RecurException(recurErrorMessage);
        }
    }
}
