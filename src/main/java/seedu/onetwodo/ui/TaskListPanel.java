package seedu.onetwodo.ui;

import static seedu.onetwodo.model.ModelManager.AUTOSCROLL_LAG;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.events.ui.SelectCardEvent;
import seedu.onetwodo.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.onetwodo.commons.util.FxViewUtil;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.TaskType;

//@@author A0143029M
/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private TaskType taskType;

    // For tests robot.lookup(#{ID})
    public static final String DEADLINE_PANEL_ID = "deadline-panel";
    public static final String EVENT_PANEL_ID = "event-panel";
    public static final String TODO_PANEL_ID = "todo-panel";

    @FXML
    private ListView<ReadOnlyTask> taskListView;

    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList, TaskType taskType) {
        super(FXML);
        this.taskType = taskType;
        setId(taskType);
        setConnections(taskList);
        addToPlaceholder(taskListPlaceholder);
    }

    private void setId(TaskType taskType) {
        switch (taskType) {
        case DEADLINE:
            taskListView.setId(DEADLINE_PANEL_ID);
            break;
        case EVENT:
            taskListView.setId(EVENT_PANEL_ID);
            break;
        case TODO:
            taskListView.setId(TODO_PANEL_ID);
            break;
        }
    }

    public int getNumberOfItems() {
        return taskListView.getItems().size();
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
        taskListView.setOnMouseClicked((MouseEvent click) -> {
            ReadOnlyTask selectedValue = taskListView.getSelectionModel().getSelectedItem();
            if (selectedValue != null) {
                logger.fine("Selection in task list panel changed to : '" + selectedValue + "'");
                raise(new TaskPanelSelectionChangedEvent(selectedValue));
            }
        });
    }

    public void scrollToAndHighlight(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
        highlightTaskCard(index);
    }

    private void highlightTaskCard(int index) {
        TimerTask highlightTask = new TimerTask() {
            @Override
            public void run() {
                EventsCenter.getInstance().post(new SelectCardEvent(index, taskType));
            }
        };
        new Timer().schedule(highlightTask, AUTOSCROLL_LAG);
    }

    public void viewScrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
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
