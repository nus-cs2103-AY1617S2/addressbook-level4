package seedu.toluist.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.storage.JsonStorage;
import seedu.toluist.storage.TodoListStorage;

/**
 * TodoList Model
 */
public class TodoList {
    private static final Logger logger = LogsCenter.getLogger(TodoList.class);
    public static final TodoListStorage DEFAULT_STORAGE = new JsonStorage();
    private static TodoList currentTodoList;

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
        try {
            TodoList todoList = storage.load();
            allTasks = todoList.getTasks();
        } catch (IOException e) {
            logger.severe("Data cannot be loaded");
        }
    }

    /**
     * Construct a todo list. use the default storage
     */
    public TodoList() {
        currentTodoList = this;
        this.storage = DEFAULT_STORAGE;
    }

    public void setStorage(TodoListStorage storage) {
        this.storage = storage;
    }

    public TodoListStorage getStorage() {
        return storage;
    }

    /**
     * Save the todo list data to disk
     * @return true / false
     */
    public boolean save() {
        currentTodoList = this;
        return storage.save(this);
    }

    /**
     * Add a task to todolist
     * @param task task to be added
     */
    public void add(Task task) {
        // Don't allow duplicate tasks
        if (allTasks.indexOf(task) > -1) {
            return;
        }

        allTasks.add(task);
    }

    /**
     * Remove a task from todo list
     * @param task task to be removed
     */
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
