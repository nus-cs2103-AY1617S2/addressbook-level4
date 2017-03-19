package seedu.task.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.FilePathChangedEvent;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskComparator;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of KIT data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList().sorted(new TaskComparator()));
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }
    
    /** Raises an event to indicate the file path has changed */
    private void indicateFilePathChanged(String newPath) {
    	raise(new FilePathChangedEvent(newPath));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void isDoneTask(int index, ReadOnlyTask target) throws TaskNotFoundException {
        int taskManagerIndex = filteredTasks.getSourceIndex(index);
        taskManager.updateDone(taskManagerIndex, target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }
    
    @Override
    public void changeFilePath(String newPath) {
    	indicateFilePathChanged(newPath);
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

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, false)));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords, boolean isExact) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, isExact)));
    }

    @Override
    public void updateFilteredTaskList(String keyword) {
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keyword)));
    }

    @Override
    public void updateFilteredTaskList(boolean value) {
        updateFilteredTaskList(new PredicateExpression(new DoneQualifier(value)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
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
        private boolean isExact = false;
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords, boolean isExact) {
            this.isExact = isExact;
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (isExact) {
                return StringUtil.containsExactWordsIgnoreCase(task.getName().fullName, nameKeyWords);
            } else {
                return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
            }
        }
        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private class TagQualifier implements Qualifier {

        private String tagKeyWord;

        TagQualifier(String tagKeyWord) {
            this.tagKeyWord = tagKeyWord;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return StringUtil.containsTagIgnoreCase(task.getTags(), tagKeyWord);
        }

        @Override
        public String toString() {
            return "Tag=" +  tagKeyWord;
        }
    }

    private class DoneQualifier implements Qualifier {

        private boolean value;

        DoneQualifier(boolean value) {
            this.value = value;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (this.value  & task.isDone()) {
                return true;
            }
            else if (!this.value  & !task.isDone()) {
                return true;
            }
            else {
                return false;
            }

        }
    }



}
