package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

/**
 * Dummy UiStore to be used why AppController is loaded
 */
public class InitialUiStore implements UiStore {
    public ObservableList<Task> getUiTasks() {
        return FXCollections.observableArrayList();
    }
}
