package seedu.onetwodo.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.onetwodo.commons.util.FxViewUtil;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.TaskType;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private TaskType taskType;

    @FXML
    private ListView<ReadOnlyTask> taskListView;

    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList,
            TaskType taskType) {
        super(FXML);
        this.taskType = taskType;
        setConnections(taskList);
        addToPlaceholder(taskListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(getFilteredTasks(taskList));
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private FilteredList<ReadOnlyTask> getFilteredTasks(ObservableList<ReadOnlyTask> taskList) {
        return new FilteredList<ReadOnlyTask>(taskList, t -> t.getTaskType() == taskType);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.setOnMouseClicked(new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent click) {
                ReadOnlyTask selectedValue = taskListView.getSelectionModel().getSelectedItem();
                if (selectedValue != null) {
                    logger.fine("Selection in task list panel changed to : '" + selectedValue + "'");
                    raise(new TaskPanelSelectionChangedEvent(selectedValue));
                }
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1, taskType.getPrefix()).getRoot());
            }
        }
    }

}
