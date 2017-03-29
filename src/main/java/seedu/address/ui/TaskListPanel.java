package seedu.address.ui;

import java.util.logging.Logger;

import com.jfoenix.controls.JFXListView;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";

    @FXML
    private JFXListView<JFXListView<ReadOnlyTask>> uilist;

    @FXML
    private JFXListView<ReadOnlyTask> todayTaskListView;

    @FXML
    private JFXListView<ReadOnlyTask> futureTaskListView;

    // height of a row should be the same as the height of a TaskCard
    final int ROW_HEIGHT = 45;
    // height of paddings after each TaskListView
    final int ROW_PADDING = 80;

    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskListToday,
            ObservableList<ReadOnlyTask> taskListFuture) {
        super(FXML);
        /*
         * Test for (int i = 0; i < 10; i++) {
         * todayTaskListView.getItems().add(new Label("" + i)); }
         */
        setConnections(todayTaskListView, taskListToday);
        setConnections(futureTaskListView, taskListFuture);
        uilist.setDepth(0);
        uilist.setExpanded(true);
        // todayTaskListView.depthProperty().set(1);
        // todayTaskListView.setExpanded(true);
        addToPlaceholder(taskListPlaceholder);
        // set ListView height, add 2 extra px to show border
        todayTaskListView.setPrefHeight(taskListToday.size() * ROW_HEIGHT + ROW_PADDING);
        futureTaskListView.setPrefHeight(taskListFuture.size() * ROW_HEIGHT + ROW_PADDING);
    }

    private void setConnections(ListView<ReadOnlyTask> taskListView, ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent(taskListView);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent(ListView<ReadOnlyTask> taskListView) {
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                // TODO: change event to recognise list type
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            futureTaskListView.scrollTo(index);
            futureTaskListView.getSelectionModel().clearAndSelect(index);
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
                setGraphic(new TaskCard(task, task.getID()).getRoot());
            }
        }
    }

}
