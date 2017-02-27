package seedu.address.model;

import java.util.ArrayList;
import java.util.Optional;

import seedu.address.model.task.Task;
import seedu.address.storage.JsonStorage;
import seedu.address.storage.Storage;

/**
 * TodoList Model
 */
public class TodoList {

    protected static Storage storage = new JsonStorage();

    private ArrayList<Task> allTasks = new ArrayList<>();

    public ArrayList<Task> getTasks() {
        return allTasks;
    }

    public static Optional<TodoList> load() {
        return storage.load();
    }

    public boolean save() {
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
