package seedu.watodo.model;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.transformation.FilteredList;
import seedu.watodo.commons.core.ComponentManager;
import seedu.watodo.commons.core.LogsCenter;
import seedu.watodo.commons.core.UnmodifiableObservableList;
import seedu.watodo.commons.events.model.TaskListChangedEvent;
import seedu.watodo.commons.util.CollectionUtil;
import seedu.watodo.commons.util.StringUtil;
import seedu.watodo.model.task.FloatingTask;
import seedu.watodo.model.task.ReadOnlyFloatingTask;
import seedu.watodo.model.task.UniqueTaskList;
import seedu.watodo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the Watodo data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList watodo;
    private final FilteredList<ReadOnlyFloatingTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given taskList and userPrefs.
     */
    public ModelManager(ReadOnlyTaskList taskList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskList, userPrefs);

        logger.fine("Initializing with Watodo: " + taskList + " and user prefs " + userPrefs);

        this.watodo = new TaskList(taskList);
        filteredTasks = new FilteredList<>(this.watodo.getTaskList());
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        watodo.resetData(newData);
        indicateWatodoChanged();
    }

    @Override
    public ReadOnlyTaskList getWatodo() {
        return watodo;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateWatodoChanged() {
        raise(new TaskListChangedEvent(watodo));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyFloatingTask target) throws TaskNotFoundException {
        watodo.removeTask(target);
        indicateWatodoChanged();
    }

    @Override
    public synchronized void addTask(FloatingTask task) throws UniqueTaskList.DuplicateTaskException {
        watodo.addTask(task);
        updateFilteredListToShowAll();
        indicateWatodoChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyFloatingTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int addressBookIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        watodo.updateTask(addressBookIndex, editedTask);
        indicateWatodoChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyFloatingTask> getFilteredTaskList() {
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
    
    @Override
    public void updateFilteredByTagsTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyFloatingTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyFloatingTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyFloatingTask task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyFloatingTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().fullDescription, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    private class TagQualifier implements Qualifier {
      private Set<String> tagKeyWords;

      TagQualifier(Set<String> tagKeyWords) {
          this.tagKeyWords = tagKeyWords;
      }

      @Override
      public boolean run(ReadOnlyFloatingTask task) {
          String tags = task
              .getTags()
              .asObservableList()
              .stream()
              .map(tag -> tag.tagName)
              .collect(Collectors.joining(" "));
          return tagKeyWords.stream()
                  .filter(keyword -> StringUtil.containsWordIgnoreCase(tags, keyword))
                  .findAny()
                  .isPresent();
      }

      @Override
      public String toString() {
          return "tag=" + String.join(", ", tagKeyWords);
      }
  }

}
