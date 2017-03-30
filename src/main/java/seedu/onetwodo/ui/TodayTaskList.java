//@@author A0141138N
package seedu.onetwodo.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.util.FxViewUtil;
import seedu.onetwodo.model.task.ReadOnlyTask;

public class TodayTaskList extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TodayTaskList.class);
    private static final String FXML = "TaskListPanel.fxml";

    // For tests robot.lookup(#{ID})
    public static final String TODAY_PANEL_ID = "today-panel";

    FilteredList<ReadOnlyTask> list;
    boolean isEmpty = false;

    @FXML
    private ListView<ReadOnlyTask> taskListView;

    public TodayTaskList(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        taskListView.setId(TODAY_PANEL_ID);
        list = getFilteredTasks(taskList);
        checkListEmpty(list);
        setConnections(list);
        addToPlaceholder(taskListPlaceholder);
    }

    private void checkListEmpty(FilteredList<ReadOnlyTask> list) {
        if (list.isEmpty()) {
            isEmpty = true;
        }
    }

    private void setConnections(FilteredList<ReadOnlyTask> list) {
        taskListView.setItems(list);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    private FilteredList<ReadOnlyTask> getFilteredTasks(ObservableList<ReadOnlyTask> taskList) {
        return new FilteredList<ReadOnlyTask>(taskList, t -> t.getTodayStatus() == true);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
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
                setGraphic(new TaskCard(task, getIndex() + 1, task.getTaskType().getPrefix()).getRoot());
            }
        }
    }

}
