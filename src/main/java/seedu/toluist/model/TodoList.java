package seedu.toluist.model;

import java.util.ArrayList;
import java.util.Optional;

import seedu.toluist.model.task.Task;
import seedu.toluist.storage.JsonStorage;
import seedu.toluist.storage.Storage;

/**
 * TodoList Model
 */
public class TodoList {

    protected static Storage storage = JsonStorage.getInstance();

    private static TodoList currentTodoList = new TodoList();

    private ArrayList<Task> allTasks = new ArrayList<>();

    public ArrayList<Task> getTasks() {
        return allTasks;
    }

    public static TodoList load() {
        Optional<TodoList> todoListOptional = storage.load();

        // Save data to file again if data is not found on disk
        if (!todoListOptional.isPresent()) {
            currentTodoList.save();
        } else {
            currentTodoList = todoListOptional.get();
        }

        return storage.load().orElse(currentTodoList);
    }

    public boolean save() {
        currentTodoList = this;
        return storage.save(this);
    }

    public void add(Task task) {

        allTasks.add(task);
    }

    public void remove(Task task) {
        allTasks.remove(task);
    }

    public void update(Task task, String description) {
        task.setDescription(description);
    }
}
