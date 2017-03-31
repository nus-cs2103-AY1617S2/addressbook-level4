package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import seedu.address.commons.events.ui.ChangeViewRequestEvent;
import seedu.address.commons.events.ui.ExpandingEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;

/**
 * Panel containing the list of groups of tasks.
 *
 * - viewTasksWithStatus decides which groups of task will be displayed by using
 *   given statusList.
 * - Upon taskList change event, the program do the following:
 *      + Save the current view (which panel is expanded and scroll position)
 *      + Reload view with new data
 *      + Restore view state
 * - taskIndexMap stores original index of tasks in taskList, because when we distribute
 *   tasks to different groups, the index of them might not be in consecutive order anymore.
 */
public class TaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";

    // Additional status to view all tasks
    private static final String ALL_TASKS = "All";
    private static final String CALENDAR = "Calendar";
    private static final String UNFINISHED = "Unfinished";

    private HashMap<String, ObservableList<ReadOnlyTask>> taskListMap;
    private HashMap<String, ArrayList<Integer>> taskIndexMap;
    private HashMap<String, TaskGroupPanel> childGroupMap;


    @FXML
    private VBox taskListView;

    private String lastExpanded;
    private int lastTaskCount;
    private double lastScrollPosition;

    private ObservableList<ReadOnlyTask> taskList;
    private List<String> statusList;

    private ListChangeListener taskListListener;

    public TaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setTaskList(taskList);
        viewTasksWithStatus(Arrays.asList(Status.DONE, UNFINISHED));
        addToPlaceholder(taskListPlaceholder);
        registerAsAnEventHandler(this);
    }

    public void setTaskList(ObservableList<ReadOnlyTask> taskList) {
        this.taskList = taskList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    private void viewTasksWithStatus(List<String> statusList) {
        setStatusList(statusList);
        initTaskListsByStatus(statusList);
        createTaskListView(statusList);

        // update taskList listener
        if (taskListListener != null) {
            taskList.removeListener(taskListListener);
        }
        taskListListener = new ListChangeListener<ReadOnlyTask>() {
            public void onChanged(Change<? extends ReadOnlyTask> change) {
                saveScrollingState(statusList);
                initTaskListsByStatus(statusList);
                createTaskListView(statusList);
                restoreScrollingState();
            }
        };
        taskList.addListener(taskListListener);
    }

    /** Update scrolling state of its child nodes */
    private void saveScrollingState(List<String> statusList) {
        lastExpanded = null;
        for (String status : statusList) {
            TaskGroupPanel childNode = childGroupMap.get(status);
            if (childNode.isExpanded()) {
                lastExpanded = childNode.getTitle();
                lastScrollPosition = childNode.getScrollPosition();
                lastTaskCount = childNode.getTaskCount();
                return;
            }
        }
    }

    /** Restore scrolling state based on previously collected info. */
    private void restoreScrollingState() {
        if (lastExpanded != null) {
            TaskGroupPanel childNode = childGroupMap.get(lastExpanded);
            if (childNode.getTaskCount() > lastTaskCount) {
                childNode.scrollToEnd();
            } else {
                childNode.scrollTo(lastScrollPosition);
            }
        }
    }

    /**
     * Groups tasks based on task status.
     *
     * A hashMap maps from status to corresponding task lists and index lists.
     * Index list contains original index of tasks in taskList.
     */
    private void initTaskListsByStatus(List<String> statusList) {
        // Clear current data
        taskListMap = new HashMap<String, ObservableList<ReadOnlyTask>>();
        taskIndexMap = new HashMap<String, ArrayList<Integer>>();
        for (String status : statusList) {
            taskListMap.put(status, FXCollections.observableArrayList());
            taskIndexMap.put(status, new ArrayList<Integer>());
        }

        // Load new data
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
            if (taskListMap.containsKey(UNFINISHED) && taskStatus != Status.DONE) {
                taskListMap.get(UNFINISHED).add(task);
                taskIndexMap.get(UNFINISHED).add(index);
            }
            index++;
        }
    }

    /**
     * Instantiate TaskGroupPanel objects and add them to taskListView.
     *
     * The last group that is non-empty will be expanded
     */
    private void createTaskListView(List<String> statusList) {
        taskListView.getChildren().clear();
        childGroupMap = new HashMap<String, TaskGroupPanel>();
        TaskGroupPanel lastOne = null;

        for (String status : statusList) {
            // Create new task group & add them to current view.
            ObservableList<ReadOnlyTask> tasks = taskListMap.get(status);
            ArrayList<Integer> taskIndexList = taskIndexMap.get(status);
            TaskGroupPanel taskGroupPanel = new TaskGroupPanel(status, tasks, taskIndexList);

            childGroupMap.put(status, taskGroupPanel);
            taskListView.getChildren().add(taskGroupPanel.getRoot());
            if (!taskIndexList.isEmpty()) {
                lastOne = taskGroupPanel;
            }
        }

        // Expand the last group that is non-empty
        if (lastOne != null) {
            lastOne.openTitlePane();
        }
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private List<String> removeDuplicateViews(List<String> views) {
        ArrayList<String> resView = new ArrayList<String>();
        for (String view : views) {
            if (!resView.contains(view)) {
                resView.add(view);
            }
        }
        return resView;
    }

    @Subscribe
    private void handleExpandingEvent(ExpandingEvent event) {
        int groupIndex = event.groupIndex;
        logger.info("User expand group by key combination: Alt + " + groupIndex);
        // groupIndex is in 1-based index, convert to 0-based
        if (groupIndex >= 1 && groupIndex <= statusList.size()) {
            String groupName = statusList.get(groupIndex - 1);
            TaskGroupPanel group = childGroupMap.get(groupName);
            group.openTitlePane();
        }
    }

    @Subscribe
    private void handleChangeViewRequestEvent(ChangeViewRequestEvent event) {
        ArrayList<String> groupsToView = new ArrayList<String>();
        for (String group : event.viewGroups) {
            if (group.equals(CALENDAR)) {
                groupsToView.addAll(Arrays.asList(Status.FLOATING, Status.OVERDUE,
                                                    Status.TODAY, Status.TOMORROW, Status.FUTURE));
            } else {
                groupsToView.add(group);
            }
        }
        viewTasksWithStatus(removeDuplicateViews(groupsToView));
        logger.info("View changed to " + String.join("|", groupsToView));
    }

}
