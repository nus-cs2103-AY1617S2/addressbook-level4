package seedu.toluist.ui.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.model.task.Task;

/**
 * TaskListUiView to display the list of tasks
 */
public class TaskListUiView extends UiView {

    private static final String FXML = "TaskListView.fxml";

    @FXML
    private ListView<Task> taskListView;

    private ObservableList<Task> taskList;


    public TaskListUiView (ObservableList<Task> taskList) {
        super(FXML);
        this.taskList = taskList;
    }

    @Override
    protected void viewDidMount () {
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
                final UiView taskRow = new TaskUiView(task, getIndex() + 1);
                setGraphic(taskRow.getRoot());
                taskRow.render();
            }
        }
    }
}
