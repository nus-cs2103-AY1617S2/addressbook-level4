package seedu.address.model;

import seedu.address.model.task.Task;
import seedu.address.storage.JsonStorage;
import seedu.address.storage.Storage;
import seedu.address.ui.UiStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by louis on 21/2/17.
 */
public class TodoList implements UiStore {
    private static TodoList instance;

    private static final Storage storage = new JsonStorage();
    private ArrayList<Task> allTasks = new ArrayList<>();
    private ArrayList<Task> lastViewedTasks = new ArrayList<>();

    public static TodoList getInstance() {
        if (instance == null) {
            try {
                instance = storage.load();
            } catch (IOException e) {
                instance = new TodoList();
            }
        }
        return instance;
    }

    public ArrayList<Task> getTasks() {
        return allTasks;
    }

    public ArrayList<Task> getLastViewedTasks() {
        return lastViewedTasks;
    }
    
    public ObservableList<Task> getUiTasks() {
        lastViewedTasks = getTasks();
        return FXCollections.observableArrayList(lastViewedTasks);
    }

    public void addTask(Task task) {
        allTasks.add(task);
        save();
    }

    public void removeTask(Task task) {
        allTasks.remove(task);
        save();
    }
    
    public void updateTask(Task task, String description) {
        task.setDescription(description);
        save();
    }

    public void save() {
        try {
            storage.save(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
