package seedu.address.ui;

import java.util.HashMap;
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
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";

    private final String groupNames[] = {
        Status.FLOATING, Status.OVERDUE, Status.TODAY, Status.TOMORROW,
        Status.THIS_WEEK, Status.IN_FUTURE
    };

    @FXML
    private ListView<TaskGroupPanel> taskListView;

    private HashMap<String, ObservableList<ReadOnlyTask>> taskListMap;

    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        initTaskListsByStatus(taskList);
        initTaskGroups();
        addToPlaceholder(taskListPlaceholder);
    }

    /**
     * Groups tasks based on task status.
     *
     * A hashMap maps from groupName to corresponding task lists.
     */
    private void initTaskListsByStatus(ObservableList<ReadOnlyTask> taskList) {
        taskListMap = new HashMap<String, ObservableList<ReadOnlyTask>>();
        for (String groupName : groupNames) {
            taskListMap.put(groupName, FXCollections.observableArrayList());
        }
        for (ReadOnlyTask task : taskList) {
            String taskStatus = task.getStatus().toString();
            for (String groupName : groupNames) {
                if (groupName.equals(taskStatus)) {
                    taskListMap.get(groupName).add(task);
                    break;
                }
            }
        }
    }

    /**
     * Instantiate TaskGroupPanel objects and add them to taskListView.
     */
    private void initTaskGroups() {
        ObservableList<TaskGroupPanel> groups = FXCollections.observableArrayList();
        for (String groupName : groupNames) {
            TaskGroupPanel taskGroupPanel = new TaskGroupPanel(groupName, taskListMap.get(groupName));
            groups.add(taskGroupPanel);
        }
        taskListView.setItems(groups);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
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

    class TaskListViewCell extends ListCell<TaskGroupPanel> {

        @Override
        protected void updateItem(TaskGroupPanel panel, boolean empty) {
            super.updateItem(panel, empty);

            if (empty || panel == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskGroupPanel(panel.getTitle(), panel.getTasks()).getRoot());
            }
        }
    }

}
