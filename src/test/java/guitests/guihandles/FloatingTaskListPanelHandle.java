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
import seedu.doit.TestApp;
import seedu.doit.model.item.FloatingTask;
import seedu.doit.model.item.ReadOnlyFloatingTask;
import seedu.doit.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the task list.
 */
public class FloatingTaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String TASK_LIST_VIEW_ID = "#floatingTaskListView";

    public FloatingTaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyFloatingTask> getSelectedFloatingTasks() {
        ListView<ReadOnlyFloatingTask> floatingTaskList = getListView();
        return floatingTaskList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyFloatingTask> getListView() {
        return getNode(TASK_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     *
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(ReadOnlyFloatingTask... tasks) {
        return this.isListMatching(0, tasks);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     *
     * @param startPosition The starting position of the sub list.
     * @param tasks         A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyFloatingTask... floatingTasks) throws IllegalArgumentException {
        if (floatingTasks.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                "Expected " + (getListView().getItems().size() - 1) + " floating tasks");
        }
        assertTrue(this.containsInOrder(startPosition, floatingTasks));
        for (int i = 0; i < floatingTasks.length; i++) {
            final int scrollTo = i + startPosition;
            this.guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            this.guiRobot.sleep(200);
            if (!TestUtil.compareCardAndFloatingTask(getFloatingTaskCardHandle(startPosition + i), floatingTasks[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getListView());
        this.guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyFloatingTask... floatingTasks) {
        List<ReadOnlyFloatingTask> floatingTasksInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + floatingTasks.length > floatingTasksInList.size()) {
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < floatingTasks.length; i++) {
            if (!floatingTasksInList.get(startPosition + i).getName().fullName.equals(floatingTasks[i].getName().fullName)) {
                return false;
            }
        }

        return true;
    }

    public FloatingTaskCardHandle navigateToTask(String name) {
        this.guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyFloatingTask> floatingTask = getListView().getItems().stream()
            .filter(p -> p.getName().fullName.equals(name))
            .findAny();
        if (!floatingTask.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToFloatingTask(floatingTask.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public FloatingTaskCardHandle navigateToFloatingTask(ReadOnlyFloatingTask floatingTask) {
        int index = getFloatingTaskIndex(floatingTask);

        this.guiRobot.interact(() -> {
            getListView().scrollTo(index);
            this.guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        this.guiRobot.sleep(100);
        return getFloatingTaskCardHandle(floatingTask);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getFloatingTaskIndex(ReadOnlyFloatingTask targetTask) {
        List<ReadOnlyFloatingTask> tasksInList = getListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if (tasksInList.get(i).getName().equals(targetTask.getName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a task from the list by index
     */
    public ReadOnlyFloatingTask getTask(int index) {
        return getListView().getItems().get(index);
    }

    public FloatingTaskCardHandle getFloatingTaskCardHandle(int index) {
        return getFloatingTaskCardHandle(new FloatingTask(getListView().getItems().get(index)));
    }

    public FloatingTaskCardHandle getFloatingTaskCardHandle(ReadOnlyFloatingTask floatingTask) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
            .filter(n -> new FloatingTaskCardHandle(this.guiRobot, this.primaryStage, n).isSameFloatingTask(floatingTask))
            .findFirst();
        if (taskCardNode.isPresent()) {
            return new FloatingTaskCardHandle(this.guiRobot, this.primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return this.guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTasks() {
        return getListView().getItems().size();
    }
}
