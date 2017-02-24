package seedu.address.ui;

import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

/**
 * Created by louis on 22/2/17.
 */
public interface UiStore {
    public ObservableList<Task> getUiTasks();
}
