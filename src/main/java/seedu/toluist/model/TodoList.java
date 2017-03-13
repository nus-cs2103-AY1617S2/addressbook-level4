package seedu.toluist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import seedu.toluist.storage.JsonStorage;
import seedu.toluist.storage.TodoListStorage;

/**
 * TodoList Model
 */
public class TodoList {

    private static TodoList currentTodoList;
    private static final TodoListStorage DEFAULT_STORAGE = new JsonStorage();

    private ArrayList<Task> allTasks = new ArrayList<>();
    @JsonIgnore
    private TodoListStorage storage = new JsonStorage();

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoList // instanceof handles nulls
                && allTasks.equals(((TodoList) other).getTasks()));
    }

    public ArrayList<Task> getTasks() {
        return allTasks;
    }

    /**
     * Load the todo list data using the default storage if currentTodoList is null
     * Otherwise returns the current in-memory todo list
     */
    public static TodoList load() {
        // Initialize currentTodoList if not done
        if (currentTodoList == null) {
            currentTodoList = new TodoList(DEFAULT_STORAGE);
        }

        return currentTodoList;
    }

    /**
     * Construct a todo list. Use the tasks saved in the storage
     * Replaces the static currentTodoList
     * @param storage a todo list storage
     */
    public TodoList(TodoListStorage storage) {
        currentTodoList = this;
        this.storage = storage;
        Optional<TodoList> todoListOptional = storage.load();
        if (todoListOptional.isPresent()) {
            allTasks = todoListOptional.get().getTasks();
        }
    }

    /**
     * Construct a todo list. use the default storage
     */
    public TodoList() {
        currentTodoList = this;
        this.storage = DEFAULT_STORAGE;
    }

    public TodoListStorage setStorage(TodoListStorage storage) {
        return storage;
    }

    public TodoListStorage getStorage() {
        return storage;
    }

    public boolean save() {
        currentTodoList = this;
        return storage.save(this);
    }

    public void add(Task task) {
        // Don't allow duplicate tasks
        if (allTasks.indexOf(task) > -1) {
            return;
        }

        allTasks.add(task);
    }

    public void remove(Task task) {
        allTasks.remove(task);
    }

    /**
     * Returns list of tasks based on predicate
     * @param predicate a predicate
     * @return a list of task
     */
    public ArrayList<Task> getFilterTasks(Predicate<Task> predicate) {
        List<Task> taskList = getTasks().stream().filter(predicate).collect(Collectors.toList());
        return new ArrayList<>(taskList);
    }
}
