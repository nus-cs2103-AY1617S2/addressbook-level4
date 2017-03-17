package savvytodo.model;

import java.util.EmptyStackException;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import savvytodo.commons.core.ComponentManager;
import savvytodo.commons.core.LogsCenter;
import savvytodo.commons.core.UnmodifiableObservableList;
import savvytodo.commons.events.model.TaskManagerChangedEvent;
import savvytodo.commons.util.CollectionUtil;
import savvytodo.commons.util.StringUtil;
import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList;
import savvytodo.model.task.UniqueTaskList.TaskNotFoundException;
import savvytodo.model.undoredo.UndoAddCommand;
import savvytodo.model.undoredo.UndoClearCommand;
import savvytodo.model.undoredo.UndoCommand;
import savvytodo.model.undoredo.UndoDeleteCommand;
import savvytodo.model.undoredo.UndoEditCommand;
import savvytodo.model.undoredo.exceptions.RedoFailureException;
import savvytodo.model.undoredo.exceptions.UndoFailureException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final UndoRedoManager undoRedoManager;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        this.undoRedoManager = new UndoRedoManager();
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        UndoClearCommand undoClear = new UndoClearCommand(taskManager, newData);
        undoRedoManager.storeUndoCommand(undoClear);
        undoRedoManager.resetRedo();
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
        UndoDeleteCommand undoDelete = new UndoDeleteCommand(target);
        undoRedoManager.storeUndoCommand(undoDelete);
        undoRedoManager.resetRedo();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        UndoAddCommand undoAdd = new UndoAddCommand(task);
        undoRedoManager.storeUndoCommand(undoAdd);
        undoRedoManager.resetRedo();
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        Task originalTask = new Task(filteredTasks.get(filteredTaskListIndex));
        UndoEditCommand undoEdit = new UndoEditCommand(filteredTaskListIndex, originalTask, editedTask);
        undoRedoManager.storeUndoCommand(undoEdit);
        undoRedoManager.resetRedo();
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
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
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().name, keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    @Override
    public void undo() throws UndoFailureException {
        try {
            UndoCommand undo = undoRedoManager.getUndoCommand();
            undo.setTaskManager(taskManager);
            undo.execute();
            indicateTaskManagerChanged();
        } catch (EmptyStackException e) {
            throw new UndoFailureException(e.getMessage());
        } catch (CommandException e) {
            throw new UndoFailureException(e.getMessage());
        }
    }

    @Override
    public void redo() throws RedoFailureException {
        try {
            UndoCommand redo = undoRedoManager.getRedoCommand();
            redo.setTaskManager(taskManager);
            redo.execute();
            indicateTaskManagerChanged();
        } catch (EmptyStackException e) {
            throw new RedoFailureException(e.getMessage());
        } catch (CommandException e) {
            throw new RedoFailureException(e.getMessage());
        }
    }
}
