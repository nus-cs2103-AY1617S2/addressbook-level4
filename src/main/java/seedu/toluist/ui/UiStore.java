package seedu.toluist.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.toluist.model.Task;
import seedu.toluist.model.TaskSwitchPredicate;

public class UiStore {
    private static UiStore instance;

    private ArrayList<Task> viewedTasks = new ArrayList<>();
    private TaskSwitchPredicate switchPredicate = TaskSwitchPredicate.INCOMPLETE_SWITCH_PREDICATE;

    public static UiStore getInstance() {
        if (instance == null) {
            instance = new UiStore();
        }
        return instance;
    }

    private UiStore() {}

    public void setSwitchPredicate(TaskSwitchPredicate switchPredicate) {
        this.switchPredicate = switchPredicate;
    }

    public TaskSwitchPredicate getSwitchPredicate() {
        return switchPredicate;
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
