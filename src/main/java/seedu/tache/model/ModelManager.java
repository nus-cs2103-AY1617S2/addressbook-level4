package seedu.tache.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.tache.commons.core.ComponentManager;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.core.UnmodifiableObservableList;
import seedu.tache.commons.events.model.TaskManagerChangedEvent;
import seedu.tache.commons.util.CollectionUtil;
import seedu.tache.commons.util.StringUtil;
import seedu.tache.model.task.DetailedTask;
import seedu.tache.model.task.ReadOnlyDetailedTask;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueDetailedTaskList.DuplicateDetailedTaskException;
import seedu.tache.model.task.UniqueTaskList;
import seedu.tache.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.tache.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyDetailedTask> filteredDetailedTasks;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        filteredDetailedTasks = new FilteredList<>(this.taskManager.getDetailedTaskList());
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

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
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
    public void updateDetailedTask(int filteredDetailedTaskListIndex, ReadOnlyDetailedTask editedDetailedTask)
            throws DuplicateDetailedTaskException {
        assert editedDetailedTask != null;

        int taskManagerIndex = filteredDetailedTasks.getSourceIndex(filteredDetailedTaskListIndex);
        taskManager.updateDetailedTask(taskManagerIndex, editedDetailedTask);
        indicateTaskManagerChanged();
        
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyDetailedTask> getFilteredDetailedTaskList() {
        return new UnmodifiableObservableList<>(filteredDetailedTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        filteredDetailedTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredDetailedTaskList(Set<String> keywords) {
        updateFilteredDetailedTaskList(new PredicateExpression(new MultiQualifier(keywords)));     
    }
    
    private void updateFilteredDetailedTaskList(Expression expression) {
        filteredDetailedTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        boolean satisfies(ReadOnlyDetailedTask task);
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
        public boolean satisfies(ReadOnlyDetailedTask detailedTask) {
            return qualifier.run(detailedTask);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        boolean run(ReadOnlyDetailedTask task);
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
        public boolean run(ReadOnlyDetailedTask detailedTask) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(detailedTask.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    private class DateQualifier implements Qualifier {
        private Set<String> dateKeyWords;

        DateQualifier(Set<String> dateKeyWords) {
            this.dateKeyWords = dateKeyWords;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return false;
        }
        
        @Override
        public boolean run(ReadOnlyDetailedTask detailedTask) {
            return dateKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(detailedTask.getStartDate().toString(), keyword))
                    .findAny()
                    .isPresent() 
                    || dateKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(detailedTask.getEndDate().toString(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "date=" + String.join(", ", dateKeyWords);
        }

    }
    
    private class TimeQualifier implements Qualifier {
        private Set<String> timeKeyWords;

        TimeQualifier(Set<String> timeKeyWords) {
            this.timeKeyWords = timeKeyWords;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return false;
        }
        
        @Override
        public boolean run(ReadOnlyDetailedTask detailedTask) {
            return timeKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(detailedTask.getStartTime().toString(), keyword))
                    .findAny()
                    .isPresent() 
                    || timeKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(detailedTask.getEndTime().toString(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "time=" + String.join(", ", timeKeyWords);
        }

    }
    
    private class MultiQualifier implements Qualifier {
        private Set<String> multiKeyWords;
        private NameQualifier nameQualifier;
        private DateQualifier dateQualifier;
        private TimeQualifier timeQualifier;

        MultiQualifier(Set<String> multiKeyWords) {
            this.multiKeyWords = multiKeyWords;
            nameQualifier = new NameQualifier(multiKeyWords);
            dateQualifier = new DateQualifier(multiKeyWords);
            timeQualifier = new TimeQualifier(multiKeyWords);
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return false;
        }
        
        @Override
        public boolean run(ReadOnlyDetailedTask detailedTask) {
            return nameQualifier.run(detailedTask) || dateQualifier.run(detailedTask) || timeQualifier.run(detailedTask);
        }

        @Override
        public String toString() {
            return "multi=" + String.join(", ", multiKeyWords);
        }

    }

    @Override
    public void deleteDetailedTask(ReadOnlyDetailedTask target) throws TaskNotFoundException {
        // TODO Auto-generated method stub

    }

    @Override
    public void addDetailedTask(DetailedTask detailedTask) throws DuplicateTaskException {
        // TODO Auto-generated method stub

    }

}
