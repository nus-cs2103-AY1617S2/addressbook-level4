package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

import java.util.ArrayList;

/**
 * Created by louis on 22/2/17.
 */
public class InitialUiStore implements UiStore {
    public ObservableList<Task> getUiTasks() {
        return FXCollections.observableArrayList();
    }
}
