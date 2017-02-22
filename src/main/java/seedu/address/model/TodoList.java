package seedu.address.model;

import seedu.address.commons.core.EventsCenter;
import seedu.address.model.task.Task;
import seedu.address.ui.UiStore;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by louis on 21/2/17.
 */
public class TodoList implements UiStore {
    private static TodoList instance;

    private final EventsCenter eventsCenter = EventsCenter.getInstance();
    private ArrayList<Task> allTasks = new ArrayList<Task>();

    public static TodoList getInstance() {
        if (instance == null) {
            instance = new TodoList();
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
    }
}
