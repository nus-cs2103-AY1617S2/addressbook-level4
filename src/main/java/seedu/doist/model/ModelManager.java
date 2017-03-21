package seedu.doist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.doist.commons.core.ComponentManager;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.core.UnmodifiableObservableList;
import seedu.doist.commons.events.model.AliasListMapChangedEvent;
import seedu.doist.commons.events.model.TodoListChangedEvent;
import seedu.doist.commons.util.CollectionUtil;
import seedu.doist.commons.util.StringUtil;
import seedu.doist.logic.commands.ListCommand.TaskType;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskPriorityComparator;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;
import seedu.doist.model.task.UniqueTaskList.TaskAlreadyFinishedException;
import seedu.doist.model.task.UniqueTaskList.TaskAlreadyUnfinishedException;
import seedu.doist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the to-do list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TodoList todoList;
    private final AliasListMap aliasListMap;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given to-do list and userPrefs.
     */
    public ModelManager(ReadOnlyTodoList todoList, ReadOnlyAliasListMap aliasListMap, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(todoList, aliasListMap, userPrefs);

        logger.fine("Initializing with To-do List: " + todoList + " aliasListMap: " + aliasListMap
                + " and user prefs " + userPrefs);

        this.todoList = new TodoList(todoList);
        this.aliasListMap = new AliasListMap(aliasListMap);
        filteredTasks = new FilteredList<>(this.todoList.getTaskList());
    }

    public ModelManager() {
        this(new TodoList(), new AliasListMap(), new UserPrefs());
    }


    //=========== AliasListMap =============================================================
    /** Raises an event to indicate the alias list model has changed */

    @Override
    public ReadOnlyAliasListMap getAliasListMap() {
        return aliasListMap;
    }

    @Override
    public void setAlias(String alias, String commandWord) {
        aliasListMap.setAlias(alias, commandWord);
        indicateAliasListMapChanged();
    }

    @Override
    public List<String> getAliasList(String defaultCommandWord) {
        return aliasListMap.getAliasList(defaultCommandWord);
    }

    @Override
    public List<String> getValidCommandList(String defaultCommandWord) {
        List<String> list = new ArrayList<String>(aliasListMap.getAliasList(defaultCommandWord));
        list.add(defaultCommandWord);
        return list;
    }

    @Override
    public Set<String> getDefaultCommandWordSet() {
        return aliasListMap.getDefaultCommandWordSet();
    }

    @Override
    public void resetToDefaultCommandWords() {
        aliasListMap.setDefaultCommandWords();
    }

    private void indicateAliasListMapChanged() {
        raise(new AliasListMapChangedEvent(aliasListMap));
    }

    //=========== TodoList =============================================================

    @Override
    public void resetData(ReadOnlyTodoList newData) {
        todoList.resetData(newData);
        indicateTodoListChanged();
    }

    @Override
    public ReadOnlyTodoList getTodoList() {
        return todoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTodoListChanged() {
        raise(new TodoListChangedEvent(todoList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        todoList.removeTask(target);
        indicateTodoListChanged();
    }

    @Override
    public synchronized void finishTask(ReadOnlyTask target) throws TaskNotFoundException,
        TaskAlreadyFinishedException {
        try {
            todoList.changeTaskFinishStatus(target, true);
        } catch (TaskAlreadyUnfinishedException e) {
            assert false : "finishTask should not try to unfinish tasks!";
        }
        indicateTodoListChanged();
    }

    @Override
    public synchronized void unfinishTask(ReadOnlyTask target) throws TaskNotFoundException,
        TaskAlreadyUnfinishedException {
        try {
            todoList.changeTaskFinishStatus(target, false);
        } catch (TaskAlreadyFinishedException e) {
            assert false : "unfinishTask should not try to finish tasks!";
        }
        indicateTodoListChanged();
    }

    @Override
    public synchronized int addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        int index = todoList.addTask(task);
        updateFilteredListToShowAll();
        indicateTodoListChanged();
        return index;
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int todoListIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        todoList.updateTask(todoListIndex, editedTask);
        indicateTodoListChanged();
    }

    @Override
    public void sortTasksByPriority() {
        todoList.sortTasks(new ReadOnlyTaskPriorityComparator());
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
        Qualifier[] qualifiers = {new DescriptionQualifier(keywords)};
        updateFilteredTaskList(new PredicateExpression(qualifiers));
    }

    @Override
    public void updateFilteredTaskList(TaskType type, UniqueTagList tags) {
        ArrayList<Qualifier> qualifiers = new ArrayList<Qualifier>();
        if (type != null) {
            qualifiers.add(new TaskTypeQualifier(type));
        }
        if (!tags.isEmpty()) {
            qualifiers.add(new TagQualifier(tags));
        }
        updateFilteredTaskList(new PredicateExpression(qualifiers.toArray(new Qualifier[qualifiers.size()])));
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

        private final Qualifier[] qualifiers;

        PredicateExpression(Qualifier[] qualifiers) {
            this.qualifiers = qualifiers;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            boolean isSatisfied = true;
            for (Qualifier qualifier : qualifiers) {
                isSatisfied = isSatisfied && qualifier.run(task);
            }
            return isSatisfied;
        }

        @Override
        public String toString() {
            return qualifiers.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class DescriptionQualifier implements Qualifier {
        private Set<String> descriptionKeyWords;

        DescriptionQualifier(Set<String> nameKeyWords) {
            this.descriptionKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return descriptionKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().desc, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", descriptionKeyWords);
        }
    }

    private class TagQualifier implements Qualifier {
        private UniqueTagList tags;

        public TagQualifier(UniqueTagList tags2) {
            this.tags = tags2;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            for (Tag tag : tags) {
                if (task.getTags().contains(tag)) {
                    return true;
                }
            }
            return false;
        }
    }

    private class TaskTypeQualifier implements Qualifier {
        private TaskType type;

        public TaskTypeQualifier(TaskType type) {
            this.type = type;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            switch (type) {
            case finished:
                return task.getFinishedStatus().getIsFinished();
            case pending:
                return !task.getFinishedStatus().getIsFinished();
            default:
                return true;
            }
        }
    }
}








