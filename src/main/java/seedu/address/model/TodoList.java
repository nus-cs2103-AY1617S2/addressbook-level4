package seedu.address.model;

import java.util.ArrayList;

import seedu.address.model.task.Task;
import seedu.address.ui.UiStore;

/**
 * TodoList Model
 */
public class TodoList {
    private ArrayList<Task> allTasks = new ArrayList<>();

    public ArrayList<Task> getTasks() {
        return allTasks;
    }

    public void addTask(Task task) {
        allTasks.add(task);
    }

    public void removeTask(Task task) {
        allTasks.remove(task);
    }

    public void updateTask(Task task, String description) {
        task.setDescription(description);
    }
}
