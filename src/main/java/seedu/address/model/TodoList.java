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
    private ArrayList<Task> allTasks = new ArrayList<Task>();

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

    public List<Task> getTasks() {
        return allTasks;
    }
    
    public ObservableList<Task> getUiTasks() {
        return FXCollections.observableArrayList(getTasks());
    }

    public void addTask(Task task) {
        allTasks.add(task);
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
