package seedu.toluist.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.toluist.model.Task;
import seedu.toluist.model.TaskSwitchPredicate;

/**
 * UiStore acts like a "single source of truth" / view model for the Ui
 * Ui will observe for changes to `shownTasks` and re-render on state change
 */
public class UiStore {
    private static UiStore instance;

    private ArrayList<Task> allTasks = new ArrayList<>();
    private TaskSwitchPredicate switchPredicate = TaskSwitchPredicate.INCOMPLETE_SWITCH_PREDICATE;
    private ObservableList<Task> shownTasks = FXCollections.observableArrayList();

    public static UiStore getInstance() {
        if (instance == null) {
            instance = new UiStore();
        }
        return instance;
    }

    private UiStore() {}

    public void setSwitchPredicate(TaskSwitchPredicate switchPredicate) {
        this.switchPredicate = switchPredicate;
        changeShownTasks();
    }

    public TaskSwitchPredicate getSwitchPredicate() {
        return switchPredicate;
    }

    public void setTasks(ArrayList<Task> tasks) {
        // Sorted by default
        Collections.sort(tasks);
        this.allTasks = tasks;
        changeShownTasks();
    }

    public ArrayList<Task> getTasks() {
        return allTasks;
    }

    /**
     * Returns list of tasks are currently shown on the Ui
     */
    public ArrayList<Task> getShownTasks() {
        return new ArrayList(Arrays.asList(shownTasks.toArray()));
    }

    public ArrayList<Task> getShownTasks(List<Integer> indexes) {
        List<Task> shownTasks = getShownTasks();
        ArrayList<Task> tasks = new ArrayList<>();
        for (int index : indexes) {
            if (index < 1 || index > shownTasks.size()) {
                continue;
            }
            tasks.add(shownTasks.get(index - 1));
        }
        return tasks;
    }

    public ObservableList<Task> getObservableTasks() {
        return shownTasks;
    }

    private void changeShownTasks() {
        shownTasks.setAll(allTasks.stream().filter(switchPredicate.getPredicate()).collect(Collectors.toList()));
    }
}
