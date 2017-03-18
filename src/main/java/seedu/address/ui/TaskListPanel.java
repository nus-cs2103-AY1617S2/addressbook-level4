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

    // Additional status to view all tasks
    private static final String ALL_TASKS = "All tasks";

    private HashMap<String, ObservableList<ReadOnlyTask>> taskListMap;
    private HashMap<String, ArrayList<Integer>> taskIndexMap;

    @FXML
    private VBox taskListView;

    private String lastShownGroupName;

    private ListChangeListener taskListListener;

    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        viewTasksWithStatus(taskList, ALL_TASKS);
        addToPlaceholder(taskListPlaceholder);
        registerAsAnEventHandler(this);
    }

    /** ViewCalendar displays tasks grouped by status */
    private void viewCalendar(ObservableList<ReadOnlyTask> taskList) {
        viewTasksWithStatus(taskList, Status.FLOATING, Status.OVERDUE, Status.TODAY,
                                        Status.TOMORROW, Status.THIS_WEEK, Status.IN_FUTURE);
    }

    private void viewTasksWithStatus(ObservableList<ReadOnlyTask> taskList, String... statusList) {
        initTaskListsByStatus(taskList, statusList);
        createTaskListView(statusList);

        // update taskList listener
        if (taskListListener != null) {
            taskList.removeListener(taskListListener);
        }
        taskListListener = new ListChangeListener<ReadOnlyTask>() {
            public void onChanged(Change<? extends ReadOnlyTask> change) {
                initTaskListsByStatus(taskList, statusList);
                createTaskListView(statusList);
            }
        };
        taskList.addListener(taskListListener);
    }

    /**
     * Groups tasks based on task status.
     *
     * A hashMap maps from status to corresponding task lists and index lists.
     * Index list contains original index of tasks in taskList.
     */
    private void initTaskListsByStatus(ObservableList<ReadOnlyTask> taskList, String[] statusList) {
        taskListMap = new HashMap<String, ObservableList<ReadOnlyTask>>();
        taskIndexMap = new HashMap<String, ArrayList<Integer>>();
        for (String status : statusList) {
            taskListMap.put(status, FXCollections.observableArrayList());
            taskIndexMap.put(status, new ArrayList<Integer>());
        }
        int index = 0;
        for (ReadOnlyTask task : taskList) {
            String taskStatus = task.getStatus().toString();
            if (taskListMap.containsKey(taskStatus)) {
                taskListMap.get(taskStatus).add(task);
                taskIndexMap.get(taskStatus).add(index);
            }
            if (taskListMap.containsKey(ALL_TASKS)) {
                taskListMap.get(ALL_TASKS).add(task);
                taskIndexMap.get(ALL_TASKS).add(index);
            }
            index++;
        }
    }

    /**
     * Instantiate TaskGroupPanel objects and add them to taskListView.
     */
    private void createTaskListView(String[] statusList) {
        taskListView.getChildren().clear();
        for (String status : statusList) {
            // Create new task group & add them to current view.
            ObservableList<ReadOnlyTask> tasks = taskListMap.get(status);
            ArrayList<Integer> taskIndexList = taskIndexMap.get(status);
            TaskGroupPanel taskGroupPanel = new TaskGroupPanel(status, tasks, taskIndexList);

            taskListView.getChildren().add(taskGroupPanel.getRoot());

            // Restore expanding state
            if (lastShownGroupName != null && lastShownGroupName.equals(status)) {
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
