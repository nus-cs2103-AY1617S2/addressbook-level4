package seedu.address.ui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

public class UiStore {
    private static UiStore instance;

    private ArrayList<Task> viewedTasks = new ArrayList<>();

    public static UiStore getInstance() {
        if (instance == null) {
            instance = new UiStore();
        }
        return instance;
    }

    public void setTask(ArrayList<Task> tasks) {
        this.viewedTasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return viewedTasks;
    }

    public ObservableList<Task> getUiTasks() {
        return FXCollections.observableArrayList(getTasks());
    }
}
