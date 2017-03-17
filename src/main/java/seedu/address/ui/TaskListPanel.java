package seedu.address.ui;

import java.util.HashMap;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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

    private HashMap<String, ObservableList<ReadOnlyTask>> taskListMap;

    @FXML
    private VBox taskListView;

    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        initTaskListsByStatus(taskList);
        initTaskGroups();
        addToPlaceholder(taskListPlaceholder);

        // When taskList changes, update everything
        taskList.addListener(new ListChangeListener<ReadOnlyTask>() {

            public void onChanged(Change<? extends ReadOnlyTask> change) {
                initTaskListsByStatus(taskList);
                initTaskGroups();
            }
        });
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
        taskListView.getChildren().clear();
        for (String groupName : groupNames) {
            TaskGroupPanel taskGroupPanel = new TaskGroupPanel(groupName, taskListMap.get(groupName));
            taskListView.getChildren().add(taskGroupPanel.getRoot());
        }
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

}
