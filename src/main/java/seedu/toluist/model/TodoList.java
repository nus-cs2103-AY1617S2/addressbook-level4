package seedu.toluist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import seedu.toluist.commons.exceptions.DataStorageException;
import seedu.toluist.storage.JsonStorage;
import seedu.toluist.storage.TodoListStorage;

/**
 * TodoList Model
 */
public class TodoList {
    private static TodoList instance;

    private ArrayList<Task> allTasks = new ArrayList<>();
    @JsonIgnore
    private TodoListStorage storage = new JsonStorage();

    public boolean equals(Object other) {
        return other == this // short circuit if same objectå
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
    public static TodoList getInstance() {
        // Initialize currentTodoList if not done
        if (instance == null) {
            instance = new TodoList();
        }

        return instance;
    }

    public void setStorage(TodoListStorage storage) {
        this.storage = storage;
    }

    public TodoListStorage getStorage() {
        return storage;
    }

    public void load() throws DataStorageException {
        TodoList loadedTodoList = storage.load();
        setTasks(loadedTodoList.getTasks());
    }

    public void load(String storagePath) throws DataStorageException {
        TodoList loadedTodoList = storage.load(storagePath);
        setTasks(loadedTodoList.getTasks());
    }

    /**
     * Save the todo list data to disk
     * @return true / false
     */
    public boolean save() {
        return storage.save(this);
    }

    /**
     * Add a task to todolist
     * @param task task to be added
     */
    public void add(Task task) {
        // Don't allow duplicate tasks
        if (allTasks.contains(task)) {
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

    public void setTasks(List<Task> newTaskList) {
        allTasks = new ArrayList<>(newTaskList);
    }
}
