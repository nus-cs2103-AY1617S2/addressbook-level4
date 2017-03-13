package seedu.toluist.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.toluist.model.Task;

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
        // Sorted by default
        Collections.sort(tasks);
        this.viewedTasks = tasks;
    }

    public Task getTask(int index) {
        return viewedTasks.get(index);
    }

    public ArrayList<Task> getTasks() {
        return viewedTasks;
    }

    public ArrayList<Task> getTasks(List<Integer> indexes) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        for (int index : indexes) {
            tasks.add(viewedTasks.get(index - 1));
        }
        return tasks;
    }

    public ObservableList<Task> getUiTasks() {
        return FXCollections.observableArrayList(getTasks());
    }
}
