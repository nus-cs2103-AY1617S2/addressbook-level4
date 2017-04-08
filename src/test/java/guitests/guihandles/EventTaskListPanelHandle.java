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
import seedu.taskmanager.TestApp;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the task list.
 */
public class EventTaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String EVENT_TASK_LIST_VIEW_ID = "#eventTaskListView";

    public EventTaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedTasks() {
        ListView<ReadOnlyTask> eventTaskList = getEventListView();
        return eventTaskList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyTask> getEventListView() {
        ListView<ReadOnlyTask> eventTaskListView = getNode(EVENT_TASK_LIST_VIEW_ID);
        return eventTaskListView;
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
        List<ReadOnlyTask> eventTasks = new ArrayList<>(Arrays.asList(tasks));
        for (int index = 0; index < eventTasks.size(); index++) {
            if (!eventTasks.get(index).isEventTask()) {
                eventTasks.remove(index);
                index--;
            }
        }
        if (eventTasks.size() + startPosition != getEventListView().getItems().size()) {
            throw new IllegalArgumentException(
                    "List size mismatched\n" + "Expected " + (getEventListView().getItems().size() - 1) + " tasks");
        }
        ReadOnlyTask[] taskArray = eventTasks.toArray(new ReadOnlyTask[eventTasks.size()]);
        assertTrue(this.containsInOrder(startPosition, taskArray));
        for (int i = 0; i < eventTasks.size(); i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getEventListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getEventTaskCardHandle(startPosition + i), eventTasks.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getEventListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order)
     * at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getEventListView().getItems();

        // Return false if the list in panel is too short to contain the given
        // list
        if (startPosition + tasks.length > tasksInList.size()) {
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getTaskName().fullTaskName
                    .equals(tasks[i].getTaskName().fullTaskName)) {
                return false;
            }
        }

        return true;
    }

    public EventTaskCardHandle navigateToEventTask(String taskname) {
        guiRobot.sleep(500); // Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getEventListView().getItems().stream()
                .filter(p -> p.getTaskName().toString().equals(taskname)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Task name not found: " + taskname);
        }

        return navigateToEventTask(task.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public EventTaskCardHandle navigateToEventTask(ReadOnlyTask task) {
        int index = getEventTaskIndex(task);

        guiRobot.interact(() -> {
            getEventListView().scrollTo(index);
            guiRobot.sleep(150);
            getEventListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getEventTaskCardHandle(task);
    }

    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found
     * in the list.
     */
    public int getEventTaskIndex(ReadOnlyTask targetTask) {
        List<ReadOnlyTask> tasksInList = getEventListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if (tasksInList.get(i).getTaskName().equals(targetTask.getTaskName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a person from the list by index
     */
    public ReadOnlyTask getEventTask(int index) {
        return getEventListView().getItems().get(index);
    }

    public EventTaskCardHandle getEventTaskCardHandle(int index) {
        return getEventTaskCardHandle(new Task(getEventListView().getItems().get(index)));
    }

    public EventTaskCardHandle getEventTaskCardHandle(ReadOnlyTask task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new EventTaskCardHandle(guiRobot, primaryStage, n).isSameTask(task)).findFirst();
        if (taskCardNode.isPresent()) {
            return new EventTaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTask() {
        return getEventListView().getItems().size();
    }
}
