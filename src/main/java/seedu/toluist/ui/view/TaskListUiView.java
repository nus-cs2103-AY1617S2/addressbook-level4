//@@author A0131125Y
package seedu.toluist.ui.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
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
        FxViewUtil.makeFullWidth(getRoot());
        configureBindings();
    }

    @Override
    protected void viewDidMount () {
        UiStore store = UiStore.getInstance();
        // So taskListView won't get refreshed another time
        taskListView.setItems(FXCollections.observableArrayList(store.getShownTasks()));
        Platform.runLater(() -> taskListView.scrollTo(store.getLastEditedTask()));
    }

    private void configureBindings() {
        UiStore store = UiStore.getInstance();
        ObservableList<Task> taskList = store.getObservableTasks();
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        store.bind(this, taskList);
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
