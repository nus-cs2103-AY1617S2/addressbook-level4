package seedu.taskmanager.ui;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.taskmanager.model.task.ReadOnlyTask;

/**
 * Panel containing the list of event tasks.
 */
public interface TaskListPanel {

    void setConnections(ObservableList<ReadOnlyTask> taskList);

    void scrollTo(int index);

    ListView<ReadOnlyTask> getEventTaskListView();

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventTaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }
}
