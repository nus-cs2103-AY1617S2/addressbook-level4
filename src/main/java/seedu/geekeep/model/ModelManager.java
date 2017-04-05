
package seedu.geekeep.model;

import java.util.ArrayList;
import java.util.Collections;
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
import seedu.geekeep.commons.events.model.GeekeepFilePathChangedEvent;
import seedu.geekeep.commons.events.model.SwitchTaskCategoryEvent;
import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.commons.util.CollectionUtil;
import seedu.geekeep.commons.util.StringUtil;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.UniqueTaskList;
import seedu.geekeep.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the GeeKeep data. All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Config config;
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
    public ModelManager(Config config, ReadOnlyGeeKeep geeKeep, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(config, geeKeep, userPrefs);

        logger.fine(
                "Initializing with" + " config " + config + " GeeKeep " + geeKeep + " and user prefs " + userPrefs);

        this.config = config;
        this.geeKeep = new GeeKeep(geeKeep);
        filteredTasks = new FilteredList<>(this.geeKeep.getTaskList());

        pastGeeKeeps = new Stack<>();
        futureGeeKeeps = new Stack<>();
        commandHistory = new ArrayList<>();
        undoableCommandHistory = new ArrayList<>();
        undoableCommandHistoryIndex = 0;

    }

    public ModelManager() {
        this(new Config(), new GeeKeep(), new UserPrefs());
    }

    @Override
    public Config getConfig() {
        return config;
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

    //@@author A0148037E
    @Override
    /**
     * Filters the task list by keywords, time and tags.
     * @param keywords, if it is empty, then every task is satisfactory. Otherwise, tasks which
     * don't match any of the keywords will be filtered out.
     * @param earlistTime, the time after which a task should happen.
     * @param latestTime, the time before which a task should happen.
     * @param tags, if it is empty, then every task is satisfactory. Otherwise, tasks which don't
     * contain any of the tags will be filtered out.
     */
    public void updateFilteredTaskList(Set<String> keywords, DateTime earlistTime,
            DateTime latestTime, UniqueTagList tags) {
        updateFilteredTaskList(new PredicateExpression(new TitleQualifier(keywords)),
                               new PredicateExpression(new TimeQualifier(earlistTime, latestTime)),
                               new PredicateExpression(new TagQualifier(tags)));
        raise(new SwitchTaskCategoryEvent(TaskCategory.ALL));
    }
    private void updateFilteredTaskList(Expression... expressions) {
        filteredTasks.setPredicate(task -> {
            boolean isSatisfactory = true;
            for (Expression expression : expressions) {
                isSatisfactory = isSatisfactory && expression.satisfies(task);
            }
            return isSatisfactory;
        });
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
    public String undo() throws NothingToUndoException {
        if (pastGeeKeeps.empty()) {
            throw new NothingToUndoException();
        }
        futureGeeKeeps.push(new GeeKeep(geeKeep));
        geeKeep.resetData(pastGeeKeeps.pop());
        indicateGeeKeepChanged();
        return undoableCommandHistory.get(--undoableCommandHistoryIndex);
    }

    @Override
    public String redo() throws NothingToRedoException {
        if (futureGeeKeeps.empty()) {
            throw new NothingToRedoException();
        }
        pastGeeKeeps.push(new GeeKeep(geeKeep));
        geeKeep.resetData(futureGeeKeeps.pop());
        indicateGeeKeepChanged();
        return undoableCommandHistory.get(undoableCommandHistoryIndex++);
    }

    @Override
    public List<String> getCommandHistory() {
        return Collections.unmodifiableList(commandHistory);
    }

    @Override
    public void appendCommandHistory(String commandText) {
        commandHistory.add(commandText);
    }

    @Override
    public void updateUndoableCommandHistory(String commandText) {
        while (undoableCommandHistory.size() > undoableCommandHistoryIndex) {
            undoableCommandHistory.remove(undoableCommandHistory.size() - 1);
        }
        undoableCommandHistory.add(commandText);
        undoableCommandHistoryIndex++;
    }

    public void updateGeekeepHistory(ReadOnlyGeeKeep originalGeekeepClone) {
        pastGeeKeeps.add(originalGeekeepClone);
        futureGeeKeeps.clear();
    }

    @Override
    public void setGeekeepFilePath(String filePath) {
        config.setGeeKeepFilePath(filePath);
        indicateGeekeepFilePathChanged();
    }

    /** Raises an event to indicate the geeKeepFilePath has changed */
    private void indicateGeekeepFilePathChanged() {
        raise(new GeekeepFilePathChangedEvent(config, geeKeep));
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
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        @Override
        String toString();
    }

    //@@author A0148037E
    private class TitleQualifier implements Qualifier {
        private Set<String> titleKeyWords;

        TitleQualifier(Set<String> nameKeyWords) {
            this.titleKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (titleKeyWords.isEmpty()) {
                //every task satisfies, because the qualifier is empty
                return true;
            } else {
                return titleKeyWords.stream()
                        .filter(keyword-> StringUtil.containsWordIgnoreCase(task.getTitle().title, keyword))
                        .findAny().isPresent();
            }
        }
    }

    private class TimeQualifier implements Qualifier {
        private DateTime earliestTime;
        private DateTime latestTime;

        private TimeQualifier(DateTime earliestTime, DateTime latestTime) {
            this.earliestTime = earliestTime;
            this.latestTime = latestTime;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return before(task) && after(task);
        }

        private boolean before(ReadOnlyTask task) {
            if (task.isFloatingTask()) { // assuming floating tasks meet all the time requirements.
                return true;
            } else {
                return task.getReferenceDateTime().compare(latestTime) <= 0;
            }
        }

        private boolean after(ReadOnlyTask task) {
            if (task.isFloatingTask()) {
                return true;
            } else {
                return task.getReferenceDateTime().compare(earliestTime) >= 0;
            }
        }
    }

    private class TagQualifier implements Qualifier {
        private UniqueTagList tags;


        private TagQualifier(UniqueTagList tags) {
            this.tags = tags;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (tags.isEmpty()) {
                //every task satisfies, because the qualifier is empty
                return true;
            } else {
                return tags.toSet().stream()
                        .filter(tag -> task.getTags().contains(tag))
                        .findAny().isPresent();
            }
        }
    }
    //@@author
}
