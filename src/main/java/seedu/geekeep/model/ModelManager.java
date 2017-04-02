
package seedu.geekeep.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.geekeep.commons.core.ComponentManager;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.commons.core.TaskCategory;
import seedu.geekeep.commons.core.UnmodifiableObservableList;
import seedu.geekeep.commons.events.model.GeeKeepChangedEvent;
import seedu.geekeep.commons.events.model.SwitchTaskCategoryEvent;
import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.commons.util.CollectionUtil;
import seedu.geekeep.commons.util.StringUtil;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.UniqueTaskList;
import seedu.geekeep.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the GeeKeep data. All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final GeeKeep geeKeep;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    //@@author A0147622H
    private final Stack<ReadOnlyGeeKeep> pastGeeKeeps;
    private final Stack<ReadOnlyGeeKeep> futureGeeKeeps;
    private final List<String> commandHistory;
    private final List<String> undoableCommandHistory;
    private int undoableCommandHistoryIndex;

    /**
     * Initializes a ModelManager with the given geekeep and userPrefs.
     */
    public ModelManager(ReadOnlyGeeKeep geeKeep, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(geeKeep, userPrefs);

        logger.fine("Initializing with GeeKeep: " + geeKeep + " and user prefs " + userPrefs);

        this.geeKeep = new GeeKeep(geeKeep);
        filteredTasks = new FilteredList<>(this.geeKeep.getTaskList());

        pastGeeKeeps = new Stack<>();
        futureGeeKeeps = new Stack<>();
        commandHistory = new ArrayList<>();
        undoableCommandHistory = new ArrayList<>();
        undoableCommandHistoryIndex = 0;

    }

    public ModelManager() {
        this(new GeeKeep(), new UserPrefs());
    }

    //@@author A0121658E
    @Override
    public void resetData(ReadOnlyGeeKeep newData) {
        GeeKeep originalGeekeepClone = new GeeKeep(geeKeep);
        geeKeep.resetData(newData);
        updateGeekeepHistory(originalGeekeepClone);
        indicateGeeKeepChanged();
    }

    @Override
    public ReadOnlyGeeKeep getGeeKeep() {
        return geeKeep;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateGeeKeepChanged() {
        raise(new GeeKeepChangedEvent(geeKeep));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        GeeKeep originalGeekeepClone = new GeeKeep(geeKeep);
        geeKeep.removeTask(target);
        updateGeekeepHistory(originalGeekeepClone);
        indicateGeeKeepChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        GeeKeep originalGeekeepClone = new GeeKeep(geeKeep);
        geeKeep.addTask(task);
        updateGeekeepHistory(originalGeekeepClone);
        updateFilteredListToShowAll();
        indicateGeeKeepChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask updatedTask)
            throws UniqueTaskList.DuplicateTaskException, IllegalValueException {
        assert updatedTask != null;

        updateGeekeepHistory(geeKeep);
        int taskListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        geeKeep.updateTask(taskListIndex, updatedTask);

        indicateGeeKeepChanged();
    }
    //@@author
    // =========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        raise(new SwitchTaskCategoryEvent(TaskCategory.ALL));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TitleQualifier(keywords)));
        raise(new SwitchTaskCategoryEvent(TaskCategory.ALL));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering =================================================

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

    private class TitleQualifier implements Qualifier {
        private Set<String> titleKeyWords;

        TitleQualifier(Set<String> nameKeyWords) {
            this.titleKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return titleKeyWords.stream()
                    .filter(keyword-> StringUtil.containsWordIgnoreCase(task.getTitle().title, keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "title=" + String.join(", ", titleKeyWords);
        }
    }

    //@@author A0121658E
    @Override
    public void markTaskDone(int filteredTaskListIndex) {
        GeeKeep originalGeekeepClone = new GeeKeep(geeKeep);
        int taskListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        geeKeep.markTaskDone(taskListIndex);
        updateGeekeepHistory(originalGeekeepClone);
        indicateGeeKeepChanged();
    }

    @Override
    public void markTaskUndone(int filteredTaskListIndex) {
        GeeKeep originalGeekeepClone = new GeeKeep(geeKeep);
        int taskListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        geeKeep.markTaskUndone(taskListIndex);
        updateGeekeepHistory(originalGeekeepClone);
        indicateGeeKeepChanged();
    }

    @Override
    public void updateFilteredTaskListToShowDone() {
        filteredTasks.setPredicate(t -> t.isDone());
        raise(new SwitchTaskCategoryEvent(TaskCategory.DONE));
    }

    @Override
    public void updateFilteredTaskListToShowUndone() {
        filteredTasks.setPredicate(t -> !t.isDone());
        raise(new SwitchTaskCategoryEvent(TaskCategory.UNDONE));
    }

    //@@author A0147622H
    @Override
    public void undo() throws NothingToUndoException {
        if (pastGeeKeeps.empty()) {
            throw new NothingToUndoException();
        }
        futureGeeKeeps.push(new GeeKeep(geeKeep));
        geeKeep.resetData(pastGeeKeeps.pop());
        indicateGeeKeepChanged();
    }

    @Override
    public void redo() throws NothingToRedoException {
        if (futureGeeKeeps.empty()) {
            throw new NothingToRedoException();
        }
        pastGeeKeeps.push(new GeeKeep(geeKeep));
        geeKeep.resetData(futureGeeKeeps.pop());
        indicateGeeKeepChanged();
    }

    @Override
    public void updateCommandHistory(String commandText) {
        commandHistory.add(commandText);
    }

    @Override
    public void updateUndoableCommandHistory(String commandText) {
        undoableCommandHistory.add(commandText);
    }

    public void updateGeekeepHistory(ReadOnlyGeeKeep originalGeekeepClone) {
        pastGeeKeeps.add(originalGeekeepClone);
        futureGeeKeeps.clear();
    }

}
