package seedu.taskmanager.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.taskmanager.commons.util.FxViewUtil;
import seedu.taskmanager.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class FloatingTaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(FloatingTaskListPanel.class);
    private static final String FXML = "FloatingTaskListPanel.fxml";

    @FXML
    private ListView<Pair<ReadOnlyTask, Integer>> floatingTaskListView;

    public FloatingTaskListPanel(AnchorPane floatingTaskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setConnections(taskList);
        addToPlaceholder(floatingTaskListPlaceholder);
    }

    public void setConnections(ObservableList<ReadOnlyTask> taskList) {
        ObservableList<Pair<ReadOnlyTask, Integer>> floatingTaskList = FXCollections.observableArrayList();
        for (int index = 0; taskList.size() != index; index++) {
            ReadOnlyTask taskToDelete = taskList.get(index);
            if (taskToDelete.isFloatingTask()) {
                Pair<ReadOnlyTask, Integer> floatingTask = new Pair<ReadOnlyTask, Integer>(taskToDelete, index);
                floatingTaskList.add(floatingTask);
            }
        }
        floatingTaskListView.setItems(floatingTaskList);
        floatingTaskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        floatingTaskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue.getKey()));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            floatingTaskListView.scrollTo(index);
            floatingTaskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<Pair<ReadOnlyTask, Integer>> {

        @Override
        protected void updateItem(Pair<ReadOnlyTask, Integer> task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {

                setGraphic(new FloatingTaskCard(task.getKey(), task.getValue() + 1).getRoot());
            }
        }
    }

    public ListView<Pair<ReadOnlyTask,Integer>> getFloatingTaskListView() {
        return this.floatingTaskListView;
    }
}
