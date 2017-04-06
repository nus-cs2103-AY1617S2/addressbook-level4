package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
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
    private ListView<ReadOnlyTask> todayTaskListView;

    @FXML
    private ListView<ReadOnlyTask> futureTaskListView;

    @FXML
    private TitledPane todayTaskListPanel;

    @FXML
    private TitledPane futureTaskListPanel;

    @FXML
    private ScrollPane scrollPane;

    ObservableList<ReadOnlyTask> taskListToday;

    ObservableList<ReadOnlyTask> taskListFuture;

    // height of a row should be the same as the height of a TaskCard
    final int rowHeight = 45;
    // height of paddings after each TaskListView
    final int rowPadding = 80;

    // @@author A0144315N
    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskListToday,
            ObservableList<ReadOnlyTask> taskListFuture) {
        super(FXML);
        this.taskListToday = taskListToday;
        this.taskListFuture = taskListFuture;
        setConnections(todayTaskListView, taskListToday);
        setConnections(futureTaskListView, taskListFuture);
        // todayTaskListView.setExpanded(true);
        addToPlaceholder(taskListPlaceholder);
        // set ListView height, add 2 extra px to show border
        updateListHeight();
    }

    public void scrollTo() {
        for (ReadOnlyTask task : taskListToday) {
            if (task.isAnimated()) {
                scrollToToday(taskListToday.indexOf(task));
            }
        }
        for (ReadOnlyTask task : taskListFuture) {
            if (task.isAnimated()) {
                scrollToToday(taskListFuture.indexOf(task));
            }
        }
    }

    public void updateListHeight() {
        todayTaskListView.setPrefHeight(taskListToday.size() * rowHeight + rowPadding);
        futureTaskListView.setPrefHeight(taskListFuture.size() * rowHeight + rowPadding);
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

    public void scrollToToday(int index) {
        Platform.runLater(() -> {
            todayTaskListView.scrollTo(index);
            todayTaskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    public void scrollToFuture(int index) {
        Platform.runLater(() -> {
            futureTaskListView.scrollTo(index);
            futureTaskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    public void showCommandCompleteAnimation(String index) {

    }

    public void showProgressbar() {

    }

    public TitledPane getTodayTaskListPanel() {
        return todayTaskListPanel;
    }

    public TitledPane getFutureTaskListPanel() {
        return futureTaskListPanel;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
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
