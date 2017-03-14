package seedu.tache.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.events.ui.DetailedTaskPanelSelectionChangedEvent;
import seedu.tache.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.tache.commons.util.FxViewUtil;
import seedu.tache.model.task.ReadOnlyDetailedTask;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private static final String FXML2 = "DetailedTaskListPanel.fxml";

    @FXML
    private ListView<ReadOnlyTask> taskListView;
    @FXML
    private ListView<ReadOnlyDetailedTask> detailedTaskListView;

    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setConnections(taskList);
        addToPlaceholder(taskListPlaceholder);
    }

    public TaskListPanel(ObservableList<ReadOnlyDetailedTask> detailedTaskList, AnchorPane taskListPlaceholder) {
        super(FXML2);
        setDetailedTaskConnections(detailedTaskList);
        addToPlaceholder(taskListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setDetailedTaskConnections(ObservableList<ReadOnlyDetailedTask> detailedTaskList) {
        detailedTaskListView.setItems(detailedTaskList);
        detailedTaskListView.setCellFactory(listView -> new DetailedTaskListViewCell());
        setEventHandlerForDTSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    private void setEventHandlerForDTSelectionChangeEvent() {
        detailedTaskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new DetailedTaskPanelSelectionChangedEvent(newValue));
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
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }

    class DetailedTaskListViewCell extends ListCell<ReadOnlyDetailedTask> {

        @Override
        protected void updateItem(ReadOnlyDetailedTask detailedTask, boolean empty) {
            super.updateItem(detailedTask, empty);

            if (empty || detailedTask == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DetailedTaskCard(detailedTask, getIndex() + 1).getRoot());
            }
        }
    }

}
