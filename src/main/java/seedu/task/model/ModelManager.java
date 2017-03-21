package seedu.task.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.task.Task;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskmanager;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager addressBook, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.taskmanager = new TaskManager(addressBook);
        filteredTasks = new FilteredList<>(this.taskmanager.getTaskList());
    }
    
    public static boolean patternStringMatch(String p, String t) {
    	int i = 0;
    	int j = 0;
    	System.out.println(p);
    	System.out.println(t);
    	while (i <= t.length() - p.length()) {
    		if (p.substring(j, j + 1).equals(t.substring(i + j, i + j + 1))) {
    			j++;
    			if (j >= p.length()) {
    				return true;
    			}
    		} else {
				j = 0;
				i++;
    		}
    	}
    	return false;
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskmanager.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskManager getAddressBook() {
        return taskmanager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskManagerChangedEvent(taskmanager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskmanager.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskmanager.addTaskTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskmanager.updateTask(taskManagerIndex, editedTask);
        indicateAddressBookChanged();
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
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
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
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	for (String s : nameKeyWords) {
        		assert s.length() > 0;
        		if (patternStringMatch(s, task.getTaskName().fullName)) {
        			return true;
        		}
        	}
        	return false;
            /*return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskName().fullName, keyword))
                    .findAny()
                    .isPresent();
                    */
        }
        

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
