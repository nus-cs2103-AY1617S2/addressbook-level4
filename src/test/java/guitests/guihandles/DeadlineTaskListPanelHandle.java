package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Pair;
import seedu.taskmanager.TestApp;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the task list.
 */
public class DeadlineTaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String DEADLINE_TASK_LIST_VIEW_ID = "#deadlineTaskListView";

    public DeadlineTaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<Pair<ReadOnlyTask,Integer>> getSelectedTasks() {
        ListView<Pair<ReadOnlyTask,Integer>> taskList = getDeadlineListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<Pair<ReadOnlyTask,Integer>> getDeadlineListView() {
        ListView<Pair<ReadOnlyTask, Integer>> deadlineListView = getNode(DEADLINE_TASK_LIST_VIEW_ID);
        return deadlineListView;
    }

    /**
     * Returns true if the list is showing the task details correctly and in
     * correct order.
     *
     * @param tasks
     *            A list of tasks in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... tasks) {
        return this.isListMatching(0, tasks);
    }

    /**
     * Returns true if the list is showing the task details correctly and in
     * correct order.
     *
     * @param startPosition
     *            The starting position of the sub list.
     * @param persons
     *            A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
        List<ReadOnlyTask> deadlineTasks = new ArrayList<>(Arrays.asList(tasks));
        for (int index = 0; index < deadlineTasks.size(); index++) {
            if (!deadlineTasks.get(index).isDeadlineTask()) {
                deadlineTasks.remove(index);
                index--;
            }
        }
        if (deadlineTasks.size() + startPosition != getDeadlineListView().getItems().size()) {
            throw new IllegalArgumentException(
                    "List size mismatched\n" + "Expected " + (getDeadlineListView().getItems().size() - 1) + " tasks");
        }
        ReadOnlyTask[] taskArray = deadlineTasks.toArray(new ReadOnlyTask[deadlineTasks.size()]);
        assertTrue(this.containsInOrder(startPosition, taskArray));
        for (int i = 0; i < deadlineTasks.size(); i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getDeadlineListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getDeadlineTaskCardHandle(startPosition + i), deadlineTasks.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getDeadlineListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order)
     * at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
        List<Pair<ReadOnlyTask,Integer>> tasksInList = getDeadlineListView().getItems();

        // Return false if the list in panel is too short to contain the given
        // list
        if (startPosition + tasks.length > tasksInList.size()) {
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getKey().getTaskName().fullTaskName
                    .equals(tasks[i].getTaskName().fullTaskName)) {
                return false;
            }
        }

        return true;
    }

    public DeadlineTaskCardHandle navigateToDeadlineTask(String taskname) {
        guiRobot.sleep(500); // Allow a bit of time for the list to be updated
        final Optional<Pair<ReadOnlyTask,Integer>> task = getDeadlineListView().getItems().stream()
                .filter(p -> p.getKey().getTaskName().toString().equals(taskname)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Task name not found: " + taskname);
        }

        return navigateToDeadlineTask(task.get().getKey());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public DeadlineTaskCardHandle navigateToDeadlineTask(ReadOnlyTask task) {
        int index = getDeadlineTaskIndex(task);

        guiRobot.interact(() -> {
            getDeadlineListView().scrollTo(index);
            guiRobot.sleep(150);
            getDeadlineListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getDeadlineTaskCardHandle(task);
    }

    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found
     * in the list.
     */
    public int getDeadlineTaskIndex(ReadOnlyTask targetTask) {
        List<Pair<ReadOnlyTask,Integer>> tasksInList = getDeadlineListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if (tasksInList.get(i).getKey().getTaskName().equals(targetTask.getTaskName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a person from the list by index
     */
    public ReadOnlyTask getTask(int index) {
        return getDeadlineListView().getItems().get(index).getKey();
    }

    public DeadlineTaskCardHandle getDeadlineTaskCardHandle(int index) {
        return getDeadlineTaskCardHandle(new Task(getDeadlineListView().getItems().get(index).getKey()));
    }

    public DeadlineTaskCardHandle getDeadlineTaskCardHandle(ReadOnlyTask task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new DeadlineTaskCardHandle(guiRobot, primaryStage, n).isSameTask(task)).findFirst();
        if (taskCardNode.isPresent()) {
            return new DeadlineTaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTask() {
        return getDeadlineListView().getItems().size();
    }
}
