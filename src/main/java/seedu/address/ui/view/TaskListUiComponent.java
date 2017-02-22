package seedu.address.ui.view;

import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.Task;

/**
 * Created by louis on 21/2/17.
 */
public class TaskListUiComponent extends UiComponent {

    private static final String FXML = "TaskListView.fxml";

    @FXML
    private ListView<Task> taskListView;

    private ObservableList<Task> taskList;


    public TaskListUiComponent(Pane parentNode, ObservableList<Task> taskList) {
        super(FXML, parentNode);
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
                final Pane node = new Pane();
                setGraphic(node);
                FxViewUtil.makeFullWidth(node);
                new TaskUiComponent(node, task, getIndex() + 1).render();
            }
        }
    }
}
