package seedu.address.ui.view;

import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * Created by louis on 21/2/17.
 */
public class TaskListView extends UiPart<Region> {

    private static final String FXML = "TaskListView.fxml";

    @FXML
    private ListView<Task> taskListView;


    public TaskListView(AnchorPane taskListViewPlaceholder, ObservableList<Task> taskList) {
        super(FXML);
        setConnections(taskList);
        addToPlaceholder(taskListViewPlaceholder);
    }

    private void setConnections(ObservableList<Task> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    class TaskListViewCell extends ListCell<Task> {

        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskView(task, getIndex() + 1).getRoot());
            }
        }
    }
}
