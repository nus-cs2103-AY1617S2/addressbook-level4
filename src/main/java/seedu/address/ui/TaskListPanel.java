package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

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
import seedu.address.commons.events.ui.ShowTaskGroupEvent;
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
    private HashMap<String, ArrayList<Integer>> taskIndexMap;

    @FXML
    private VBox taskListView;

    private String lastShownGroupName;

    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        initTaskListsByStatus(taskList);
        createTaskListView();
        addToPlaceholder(taskListPlaceholder);
        registerAsAnEventHandler(this);

        // When taskList changes, update everything
        taskList.addListener(new ListChangeListener<ReadOnlyTask>() {
            public void onChanged(Change<? extends ReadOnlyTask> change) {
                initTaskListsByStatus(taskList);
                createTaskListView();
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
        taskIndexMap = new HashMap<String, ArrayList<Integer>>();
        for (String groupName : groupNames) {
            taskListMap.put(groupName, FXCollections.observableArrayList());
            taskIndexMap.put(groupName, new ArrayList<Integer>());
        }
        int index = 0;
        for (ReadOnlyTask task : taskList) {
            String taskStatus = task.getStatus().toString();
            taskListMap.get(taskStatus).add(task);
            taskIndexMap.get(taskStatus).add(index);
            index++;
        }
    }

    /**
     * Instantiate TaskGroupPanel objects and add them to taskListView.
     */
    private void createTaskListView() {
        taskListView.getChildren().clear();
        for (String groupName : groupNames) {
            ObservableList<ReadOnlyTask> tasks = taskListMap.get(groupName);
            ArrayList<Integer> taskIndexList = taskIndexMap.get(groupName);
            TaskGroupPanel taskGroupPanel = new TaskGroupPanel(groupName, tasks, taskIndexList);
            taskListView.getChildren().add(taskGroupPanel.getRoot());

            // Restore expanding state
            if (lastShownGroupName != null && lastShownGroupName.equals(groupName)) {
                taskGroupPanel.openTitlePane();
            }
        }
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    @Subscribe
    private void handleShowTaskGroupEvent(ShowTaskGroupEvent event) {
        lastShownGroupName = event.title;
    }

}
