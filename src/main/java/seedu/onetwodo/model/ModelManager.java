package seedu.onetwodo.model;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.onetwodo.commons.core.ComponentManager;
import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.core.UnmodifiableObservableList;
import seedu.onetwodo.commons.events.model.ToDoListChangedEvent;
import seedu.onetwodo.commons.events.ui.JumpToListRequestEvent;
import seedu.onetwodo.commons.events.ui.ShowTagsRequestEvent;
import seedu.onetwodo.commons.exceptions.EmptyHistoryException;
import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.commons.util.CollectionUtil;
import seedu.onetwodo.commons.util.StringUtil;
import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.EditCommand;
import seedu.onetwodo.logic.commands.UndoneCommand;
import seedu.onetwodo.model.history.ToDoListHistoryManager;
import seedu.onetwodo.model.tag.Tag;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Priority;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.StartDate;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.model.task.UniqueTaskList;
import seedu.onetwodo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the todo list data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    // toDoList is data, not observable
    private final ToDoList toDoList;

    // filteredTasks is observable
    private final FilteredList<ReadOnlyTask> filteredTasks;

    // All commands except find clears the search String filter
    private Set<String> searchStrings;

    // To persist on list command
    private DoneStatus doneStatus;

    private ToDoListHistoryManager history;

    /**
     * Initializes a ModelManager with the given toDoList and userPrefs.
     */
    public ModelManager(ReadOnlyToDoList toDoList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(toDoList, userPrefs);

        logger.fine("Initializing with toDo list: " + toDoList + " and user prefs " + userPrefs);

        this.toDoList = new ToDoList(toDoList);
        this.filteredTasks = new FilteredList<>(this.toDoList.getTaskList());
        this.searchStrings = new HashSet<String>();
        this.doneStatus = DoneStatus.UNDONE;
        this.history = new ToDoListHistoryManager();
    }

    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        toDoList.resetData(newData);
        indicateToDoListChanged();
    }

    @Override
    public void resetSearchStrings() {
        this.searchStrings = new HashSet<String>();
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return toDoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged() {
        raise(new ToDoListChangedEvent(toDoList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        ToDoList copiedCurrentToDoList = new ToDoList(this.toDoList);
        toDoList.removeTask(target);
        history.saveUndoInformationAndClearRedoHistory(DeleteCommand.COMMAND_WORD, target, copiedCurrentToDoList);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void doneTask(ReadOnlyTask taskToComplete) throws IllegalValueException {
        if (taskToComplete.getDoneStatus() == true) {
            throw new IllegalValueException("This task has been done");
        }
        ToDoList copiedCurrentToDoList = new ToDoList(this.toDoList);
        if (!taskToComplete.hasRecur()) {
            toDoList.doneTask(taskToComplete);
        } else {
            Task newTask = new Task(taskToComplete);
            newTask.updateTaskRecurDate(true);
            toDoList.doneTask(taskToComplete);
            toDoList.addTask(newTask);
            jumpToNewTask(newTask);
        }

        history.saveUndoInformationAndClearRedoHistory(DoneCommand.COMMAND_WORD, taskToComplete, copiedCurrentToDoList);
        indicateToDoListChanged();
    }

    //@@author A0139343E
    @Override
    public synchronized void undoneTask(ReadOnlyTask taskToUncomplete)
            throws IllegalValueException, TaskNotFoundException {

        if (taskToUncomplete.getDoneStatus() == false) {
            throw new IllegalValueException(UndoneCommand.MESSAGE_UNDONE_UNDONE_TASK);
        }
        ToDoList copiedCurrentToDoList = new ToDoList(this.toDoList);
        Task copiedTask = new Task(taskToUncomplete);
        copiedTask.updateTaskRecurDate(true);
        copiedTask.setUndone();
        ReadOnlyTask taskToCheck = copiedTask;

        if (!taskToUncomplete.hasRecur()) {
            undoneNonRecur(taskToUncomplete);

        } else if (toDoList.contains(taskToCheck)) {
            int index = toDoList.getTaskList().indexOf(taskToCheck);
            if(toDoList.getTaskList().get(index).getDoneStatus() == false) {
                undoneLatestRecur(taskToUncomplete, taskToCheck);
            } else {
                undoneNonLatestRecur(taskToUncomplete);
            }

        } else {
            undoneNonParentRecur(taskToUncomplete);
        }

        history.saveUndoInformationAndClearRedoHistory(UndoneCommand.COMMAND_WORD,
                taskToUncomplete, copiedCurrentToDoList);
        indicateToDoListChanged();
    }

    private void undoneNonRecur(ReadOnlyTask taskToUncomplete) {
        toDoList.undoneTask(taskToUncomplete);
    }

    private void undoneLatestRecur(ReadOnlyTask taskToUncomplete, ReadOnlyTask taskToRevertBackward) throws
            TaskNotFoundException {
        toDoList.removeTask(taskToUncomplete);
        toDoList.backwardRecur(taskToRevertBackward);
    }

    private void undoneNonLatestRecur(ReadOnlyTask taskToUncomplete) {
        undoneNonParentRecur(taskToUncomplete);
    }

    private void undoneNonParentRecur(ReadOnlyTask taskToUncomplete) {
        ReadOnlyTask recurRemovedTask = toDoList.removeRecur(taskToUncomplete);
        toDoList.undoneTask(recurRemovedTask);
    }

    //@@author
    @Override
    public synchronized void sortBy(SortOrder sortOrder, boolean isReversed) {
        toDoList.sortTasks(sortOrder, isReversed);
        indicateToDoListChanged();
    }

    //@@author
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        ToDoList copiedCurrentToDoList = new ToDoList(this.toDoList);
        toDoList.addTask(task);
        history.saveUndoInformationAndClearRedoHistory(AddCommand.COMMAND_WORD, task, copiedCurrentToDoList);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void updateTask(ReadOnlyTask taskToEdit, int internalIdx, Task editedTask)
            throws TaskNotFoundException, UniqueTaskList.DuplicateTaskException {
        assert taskToEdit != null;
        assert editedTask != null;

        ToDoList copiedCurrentToDoList = new ToDoList(this.toDoList);
        toDoList.removeTask(taskToEdit);
        toDoList.addTask(internalIdx, editedTask);

        history.saveUndoInformationAndClearRedoHistory(EditCommand.COMMAND_WORD, taskToEdit,
                editedTask, copiedCurrentToDoList);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void addTaskForEdit(int internalIdx, Task editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(internalIdx, editedTask);
        indicateToDoListChanged();
    }

    //@@author A0135739W
    @Override
    public synchronized String undo() throws EmptyHistoryException {
        if (history.hasUndoHistory()) {
            history.saveRedoInformation(this.toDoList);
            this.toDoList.resetData(history.getPreviousToDoList());
            String feedbackMessage = history.getUndoFeedbackMessageAndTransferToRedo();
            indicateToDoListChanged();
            return feedbackMessage;
        } else {
            throw new EmptyHistoryException(ToDoListHistoryManager.MESSAGE_EMPTYUNDOHISTORY);
        }
    }

    //@@author A0135739W
    @Override
    public synchronized String redo() throws EmptyHistoryException {
        if (history.hasRedoHistory()) {
            history.saveUndoInformation(this.toDoList);
            this.toDoList.resetData(history.getNextToDoList());
            String feedbackMessage = history.getRedoFeedbackMessageAndTransferToUndo();
            indicateToDoListChanged();
            return feedbackMessage;
        } else {
            throw new EmptyHistoryException(ToDoListHistoryManager.MESSAGE_EMPTYREDOHISTORY);
        }
    }

    @Override
    public void clear() {
        ToDoList copiedCurrentToDoList = new ToDoList(this.toDoList);
        history.saveUndoInformationAndClearRedoHistory(ClearCommand.COMMAND_WORD, copiedCurrentToDoList);
        resetData(new ToDoList());
    }

    //@@author A0135739W
    @Override
    public void clearDone() {
        toDoList.clearDone();
        indicateToDoListChanged();
    }

    //@@author A0135739W
    @Override
    public void clearUndone() {
        toDoList.clearUndone();
        indicateToDoListChanged();
    }

    //@@author A0135739W
    @Override
    public void displayTags() {
        String tagsListToDisplay = "";
        for (Tag tag: toDoList.getTagList()) {
            tagsListToDisplay += tag.toString();
            tagsListToDisplay += "\n";
        }
        EventsCenter.getInstance().post(new ShowTagsRequestEvent(tagsListToDisplay));
    }

    //@@author
    // =========== Filtered Task List Accessors
    // =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    //@@author A0139343E
    @Override
    public void updateByNameDescriptionTag(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new MainKeywordsQualifier(keywords)));
        searchStrings = keywords;
    }

    //@@author
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredUndoneTaskList() {
        updateFilteredTaskList(new PredicateExpression(p -> p.getDoneStatus() == false));
    }

    @Override
    public void updateFilteredDoneTaskList() {
        updateFilteredTaskList(new PredicateExpression(p -> p.getDoneStatus() == true));
    }

    @Override
    public void updateFilteredTodayTaskList() {
        updateFilteredTaskList(new PredicateExpression(p -> p.getTodayStatus() == true));
    }

    @Override
    public void updateByTaskType(TaskType taskType) {
        updateFilteredTaskList(new PredicateExpression(p -> p.getTaskType() == taskType));
    }

    @Override
    public void updateByDoneDatePriorityTags(EndDate before, StartDate after, Priority priority, Set<Tag> tags) {
        boolean hasBefore = before.hasDate();
        boolean hasAfter = after.hasDate();
        updateFilteredTaskList(new PredicateExpression(task -> isTaskSameDoneStatus(task, doneStatus)
                && (hasBefore ? isTaskBefore(task, before) : true)
                && (hasAfter ? isTaskAfter(task, after) : true)
                && (priority.hasPriority() ? isPrioritySame(task, priority) : true)
                && (!tags.isEmpty() ? containsAnyTag(task, tags) : true)
                ));
    }

    private boolean containsAnyTag(ReadOnlyTask task, Set<Tag> tags) {
        Set<Tag> tagsRemoved = task.getTags().toSet();
        tagsRemoved.removeAll(tags);
        return tagsRemoved.size() != task.getTags().toSet().size();
    }

    private boolean isPrioritySame(ReadOnlyTask task, Priority p) {
        return task.getPriority().value.equals(p.value);
    }

    private boolean isTaskSameDoneStatus(ReadOnlyTask task, DoneStatus doneStatus) {
        switch (doneStatus) {
        case DONE:
            return task.getDoneStatus() == true;
        case UNDONE:
            return task.getDoneStatus() == false;
        case ALL:
        default:
            return true;
        }
    }

    private boolean isTaskBefore(ReadOnlyTask task, EndDate endDate) {
        switch (task.getTaskType()) {
        case DEADLINE:
            return task.getEndDate().getLocalDateTime().isBefore(endDate.getLocalDateTime());
        case EVENT:
            return task.getStartDate().getLocalDateTime().isBefore(endDate.getLocalDateTime());
        case TODO:
        default:
            return true;
        }
    }

    private boolean isTaskAfter(ReadOnlyTask task, StartDate startDate) {
        switch (task.getTaskType()) {
        case DEADLINE:
        case EVENT:
            return task.getEndDate().getLocalDateTime().isAfter(startDate.getLocalDateTime());
        case TODO:
        default:
            return true;
        }
    }

    @Override
    public void updateByDoneStatus() {
        switch (doneStatus) {
        case DONE:
            updateFilteredDoneTaskList();
            break;
        case UNDONE:
            updateFilteredUndoneTaskList();
            break;
        case ALL:
            updateFilteredListToShowAll();
            break;
        }
    };

    @Override
    public void updateBySearchStrings() {
        if (searchStrings.size() > 0) {
            updateByNameDescriptionTag(searchStrings);
        }
    }

    @Override
    public FilteredList<ReadOnlyTask> getFilteredByDoneFindType(TaskType type) {
        // update by find before getting
        updateBySearchStrings();

        // filter by type
        FilteredList<ReadOnlyTask> filtered = getFilteredTaskList().filtered(t -> t.getTaskType() == type);

        // filter by done and return
        switch (doneStatus) {
        case DONE:
            return filtered.filtered(t -> t.getDoneStatus() == true);
        case UNDONE:
            return filtered.filtered(t -> t.getDoneStatus() == false);
        case ALL:
        default:
            return filtered;
        }
    }

    @Override
    public int getTaskIndex(ReadOnlyTask task) {
        FilteredList<ReadOnlyTask> filtered = getFilteredByDoneFindType(task.getTaskType());
        return filtered.indexOf(task);
    }

    /**
     * Scroll to task provided
     *
     * @param task to jump to
     */
    @Override
    public void jumpToNewTask(ReadOnlyTask task) {
        int filteredIndex = getTaskIndex(task);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        EventsCenter.getInstance().post(new JumpToListRequestEvent(filteredIndex, task.getTaskType()));
                    }
                }, 300);
    }

    // ========== Inner classes/interfaces used for filtering
    @Override
    public DoneStatus getDoneStatus() {
        return doneStatus;
    }

    @Override
    public void setDoneStatus(DoneStatus doneStatus) {
        this.doneStatus = doneStatus;
    }

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        @Override
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

        @Override
        String toString();
    }

    //@@author A0139343E
    /**
     * A qualifier that look for a task's main keywords.
     * The main keywords that define a task is by its name, description and tags.
     *
     */
    private class MainKeywordsQualifier implements Qualifier {
        private Set<String> keyWords;

        MainKeywordsQualifier(Set<String> keyWords) {
            this.keyWords = keyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            String taskName = task.getName().fullName;
            String taskDescription = task.getDescription().value;
            String taskTagNames = task.getTags().combineTagString();
            StringBuilder sb = new StringBuilder();
            sb.append(taskName);
            sb.append(" " + taskDescription);
            sb.append(" " + taskTagNames);
            String combinedString = sb.toString();

            return keyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(combinedString, keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "name =" + String.join(", ", keyWords);
        }
    }
    //@@author

}
