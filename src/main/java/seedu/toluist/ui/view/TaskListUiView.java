package seedu.toluist.ui.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.model.Task;
import seedu.toluist.ui.UiStore;

/**
 * TaskListUiView to display the list of tasks
 */
public class TaskListUiView extends UiView {

    private static final String FXML = "TaskListView.fxml";

    @FXML
    private ListView<Task> taskListView;

    public TaskListUiView () {
        super(FXML);
    }

    @Override
    protected void viewDidMount () {
        UiStore store = UiStore.getInstance();
        ObservableList<Task> taskList = store.getUiTasks();
        setConnections(taskList);
        FxViewUtil.makeFullWidth(getRoot());
    }

    private void setConnections(ObservableList<Task> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    class TaskListViewCell extends ListCell<Task> {

        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                TaskUiView taskRow = new TaskUiView(task, getIndex() + 1);
                setGraphic(taskRow.getRoot());
                taskRow.render();
            }
        }
    }
}
