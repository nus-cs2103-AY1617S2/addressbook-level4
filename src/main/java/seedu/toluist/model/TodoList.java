package seedu.toluist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.toluist.storage.JsonStorage;
import seedu.toluist.storage.TodoListStorage;

/**
 * TodoList Model
 */
public class TodoList {

    private static TodoListStorage storage = new JsonStorage();

    private static TodoList currentTodoList;

    private ArrayList<Task> allTasks = new ArrayList<>();

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoList // instanceof handles nulls
                && allTasks.equals(((TodoList) other).getTasks()));
    }

    public ArrayList<Task> getTasks() {
        return allTasks;
    }

    public static void setStorage(TodoListStorage storage) {
        TodoList.storage = storage;
    }

    public static TodoList load() {
        // Initialize currentTodoList if not done
        if (currentTodoList == null) {
            currentTodoList = storage.load().orElse(new TodoList());
        }

        return currentTodoList;
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
