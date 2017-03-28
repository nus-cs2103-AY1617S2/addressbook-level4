package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.onetwodo.TestApp;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestUtil;
import seedu.onetwodo.ui.TaskListPanel;

/**
 * Provides a handle for the panel containing the task list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";
    private static final String TASK_LIST_VIEW_ID = "#taskListView";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedTasks() {
        ListView<ReadOnlyTask> taskList = getListView(TaskType.DEADLINE);
        return taskList.getSelectionModel().getSelectedItems();
    }

    public List<ReadOnlyTask> getSelectedTasks(TaskType taskType) {
        ListView<ReadOnlyTask> taskList = getListView(taskType);
        return taskList.getSelectionModel().getSelectedItems();
    }

    /**
     * @param taskType
     * @return ListView node by TaskType using the task panel ID set on initialization.
     */
    public ListView<ReadOnlyTask> getListView(TaskType taskType) {
        switch (taskType) {
        case EVENT:
            return getNode("#" + TaskListPanel.EVENT_PANEL_ID);
        case TODO:
            return getNode("#" + TaskListPanel.TODO_PANEL_ID);
        case DEADLINE:
        default:
            return getNode("#" + TaskListPanel.DEADLINE_PANEL_ID);
        }
    }

    /**
     * @return ListView containing all the lists
     */
    public ListView<ReadOnlyTask> getListView() {
        return getNode(TASK_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param taskType of current list panel handle & list under comparison.
     * @param tasks A list of task in the correct order to compare with panel.
     */
    public boolean isListMatching(TaskType taskType, ReadOnlyTask... tasks) {
        return this.isListMatching(0, taskType, tasks);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param taskType of current list panel handle & list under comparison.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, TaskType taskType, ReadOnlyTask... tasks)
            throws IllegalArgumentException {

        ListView<ReadOnlyTask> listView = getListView(taskType);
        ReadOnlyTask[] filteredTasks = TestUtil.getTasksByTaskType(tasks, taskType);

        if (filteredTasks.length + startPosition != listView.getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (listView.getItems().size() - 1) + " tasks");
        }

        assertTrue(this.containsInOrder(startPosition, taskType, tasks));
        for (int i = 0; i < filteredTasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> listView.scrollTo(scrollTo));
            guiRobot.sleep(200);
            TaskCardHandle taskCard = getTaskCardHandle(taskType, startPosition + i);

            if (!TestUtil.compareCardAndTask(taskCard, filteredTasks[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     * Defaults to deadline list view if not specified
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getListView(TaskType.DEADLINE));
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, TaskType taskType, ReadOnlyTask... tasks) {
        ListView<ReadOnlyTask> listView = getListView(taskType);
        List<ReadOnlyTask> tasksInList = listView.getItems();
        ReadOnlyTask[] filteredTasks = TestUtil.getTasksByTaskType(tasks, taskType);

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + filteredTasks.length > tasksInList.size()) {
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < filteredTasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getName().fullName
                    .equals(filteredTasks[i].getName().fullName)) {
                return false;
            }
        }

        return true;
    }

    public TaskCardHandle navigateToTask(TaskType taskType, String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getListView(taskType).getItems().stream()
                                                    .filter(p -> p.getName().fullName.equals(name))
                                                    .findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToTask(task.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public TaskCardHandle navigateToTask(ReadOnlyTask task) {
        int index = getTaskIndex(task);
        guiRobot.interact(() -> {
            getListView(task.getTaskType()).scrollTo(index);
            guiRobot.sleep(150);
            getListView(task.getTaskType()).getSelectionModel().select(index);
        });
        guiRobot.sleep(200);
        return getTaskCardHandle(task);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(ReadOnlyTask targetTask) {
        ListView<ReadOnlyTask> listView = getListView(targetTask.getTaskType());
        List<ReadOnlyTask> tasksInList = listView.getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if (tasksInList.get(i).getName().equals(targetTask.getName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a task from the list by taskType and index
     */
    public ReadOnlyTask getTask(TaskType taskType, int index) {
        return getListView(taskType).getItems().get(index);
    }

    /**
     * Gets a TaskCardHandle from the list by taskType and index
     */
    public TaskCardHandle getTaskCardHandle(TaskType taskType, int index) {
        return getTaskCardHandle(new Task(getListView(taskType).getItems().get(index)));
    }

    public TaskCardHandle getTaskCardHandle(ReadOnlyTask task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new TaskCardHandle(guiRobot, primaryStage, n).isSameTask(task))
                .findFirst();
        if (taskCardNode.isPresent()) {
            return new TaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTask() {
        return getListView(TaskType.DEADLINE).getItems().size()
                + getListView(TaskType.EVENT).getItems().size()
                + getListView(TaskType.TODO).getItems().size();
    }

    public int getNumberOfTask(TaskType taskType) {
        return getListView(taskType).getItems().size();
    }
}
