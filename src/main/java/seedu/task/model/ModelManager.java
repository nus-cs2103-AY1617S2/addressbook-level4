package seedu.task.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final YTomorrow taskManager;
    private final History<ReadOnlyTaskManager> history;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    //@@author A0164889E
    private final FilteredList<ReadOnlyTask> filteredTasksComplete;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new YTomorrow(taskManager);
        this.history = new History<ReadOnlyTaskManager>();
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        //@@author A0164889E
        filteredTasksComplete = new FilteredList<>(this.taskManager.getTaskList());

        indicateCompleteListToChange();
        //@@author

        history.push(taskManager);
    }

    public ModelManager() {
        this(new YTomorrow(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
        //@@author A0164889E
        indicateCompleteListToChange();
        //@@author
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        addToHistory(new YTomorrow(taskManager));
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
        //@@author A0164889E
        indicateCompleteListToChange();
        //@@author
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
        //@@author A0164889E
        indicateCompleteListToChange();
        //@@author
    }

    @Override
    public void updateTask(int filteredTasksListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTasksListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
        //@@author A0164889E
        indicateCompleteListToChange();
        //@@author
    }

    //@@author A0163848R
    @Override
    public boolean undoLastModification() {
        ReadOnlyTaskManager undone = history.undo();
        if (undone != null) {
            taskManager.resetData(undone);
            raise(new TaskManagerChangedEvent(taskManager));
            indicateCompleteListToChange();
            return true;
        }
        return false;
    }

    @Override
    public boolean redoLastModification() {
        ReadOnlyTaskManager redone = history.redo();
        if (redone != null) {
            taskManager.resetData(redone);
            raise(new TaskManagerChangedEvent(taskManager));
            indicateCompleteListToChange();
            return true;
        }
        return false;
    }

    @Override
    public void mergeYTomorrow(ReadOnlyTaskManager add) {
        for (ReadOnlyTask readOnlyTask : add.getTaskList()) {
            Task task = new Task(readOnlyTask);
            try {
                taskManager.addTask(task);
            } catch (DuplicateTaskException e) {
                try {
                    taskManager.removeTask(task);
                    taskManager.addTask(task);
                } catch (TaskNotFoundException | DuplicateTaskException el) {
                }
            }
        }
        indicateTaskManagerChanged();
        indicateCompleteListToChange();
    }
    //@@author

    @Override
    public void addToHistory(ReadOnlyTaskManager state) {
        history.push(state);
    }

    //@@author A0164889E
    public void indicateCompleteListToChange() {
        UniqueTagList tags;
        try {
            tags = new UniqueTagList("complete");
            updateFilteredTaskListTag(tags);
        } catch (DuplicateTagException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    //@@author A0164466X
    @Override
    public void updateFilteredListToShowComplete() {
        try {
            updateFilteredTaskList(new PredicateExpression(new TagQualifier(new UniqueTagList(Tag.TAG_COMPLETE))));
        } catch (DuplicateTagException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateFilteredListToShowIncomplete() {
        try {
            updateFilteredTaskList(new PredicateExpression(new TagQualifier(new UniqueTagList(Tag.TAG_INCOMPLETE))));
        } catch (DuplicateTagException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //@@author A0164889E
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListComplete() {
        return new UnmodifiableObservableList<>(filteredTasksComplete);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //@@author A0164889E
    @Override
    public void updateFilteredTaskListGroup(Set<String> keywords) {
        updateFilteredTaskListGroup(new PredicateExpression(new GroupQualifier(keywords)));
    }

    private void updateFilteredTaskListGroup(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    public void updateFilteredTaskListTag(UniqueTagList keywords) {
        updateFilteredTaskListTag(new PredicateExpression(new TagQualifier(keywords)));
    }

    private void updateFilteredTaskListTag(Expression expression) {
        filteredTasksComplete.setPredicate(expression::satisfies);
    }
    //@@author

    //========== Inner classes/interfaces used for filtering =================================================

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

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    //@@author A0164889E
    private class GroupQualifier implements Qualifier {
        private Set<String> groupKeyWords;

        GroupQualifier(Set<String> groupKeyWords) {
            this.groupKeyWords = groupKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return groupKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getGroup().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", groupKeyWords);
        }
    }

    //@@author A0164466X
    private class TagQualifier implements Qualifier {
        private UniqueTagList tags;

        TagQualifier(UniqueTagList tags) {
            this.tags = tags;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getTags().equals(tags);
        }

        //Default toString() method used

    }

}
