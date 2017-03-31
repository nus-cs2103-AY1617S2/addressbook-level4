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
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        configureBindings();
    }

    @Override
    protected void viewDidMount () {
        UiStore store = UiStore.getInstance();
        taskListView.setItems(FXCollections.observableArrayList());
        taskListView.setItems(FXCollections.observableArrayList(store.getShownTasks()));
        int scrollIndex = Math.max(store.getShownTasks().indexOf(store.getLastEditedTask()) - 1, 0);
        Platform.runLater(() -> taskListView.scrollTo(scrollIndex));
    }

    private void configureBindings() {
        UiStore store = UiStore.getInstance();
        ObservableList<Task> taskList = store.getObservableTasks();
        store.bind(this, taskList);
        store.bind(this, store.getObservableSwitchPredicate());
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
