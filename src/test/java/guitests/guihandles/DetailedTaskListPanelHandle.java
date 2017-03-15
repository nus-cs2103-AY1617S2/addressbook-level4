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
import seedu.tache.TestApp;
import seedu.tache.model.task.DetailedTask;
import seedu.tache.model.task.ReadOnlyDetailedTask;
import seedu.tache.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the task list.
 */
public class DetailedTaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String TASK_LIST_VIEW_ID = "#detailedTaskListView";

    public DetailedTaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyDetailedTask> getSelectedDetailedTasks() {
        ListView<ReadOnlyDetailedTask> taskList = getListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyDetailedTask> getListView() {
        return getNode(TASK_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(ReadOnlyDetailedTask... tasks) {
        return this.isListMatching(0, tasks);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyDetailedTask... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndDetailedTask(getDetailedTaskCardHandle(startPosition + i), tasks[i])) {
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
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyDetailedTask... detailedTasks) {
        List<ReadOnlyDetailedTask> detailedTasksInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + detailedTasks.length > detailedTasksInList.size()) {
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < detailedTasks.length; i++) {
            if (!detailedTasksInList.get(startPosition + i).getName()
                 .fullName.equals(detailedTasks[i].getName().fullName)) {
                return false;
            }
        }

        return true;
    }

    public DetailedTaskCardHandle navigateToDetailedTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyDetailedTask> detailedTask = getListView().getItems().stream()
                                                    .filter(p -> p.getName().fullName.equals(name))
                                                    .findAny();
        if (!detailedTask.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToTask(detailedTask.get());
    }

    /**
     * Navigates the listview to display and select the detailed task.
     */
    public DetailedTaskCardHandle navigateToTask(ReadOnlyDetailedTask detailedTask) {
        int index = getDetailedTaskIndex(detailedTask);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getDetailedTaskCardHandle(detailedTask);
    }


    /**
     * Returns the position of the detailed task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getDetailedTaskIndex(ReadOnlyDetailedTask targetDetailedTask) {
        List<ReadOnlyDetailedTask> tasksInList = getListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if (tasksInList.get(i).getName().equals(targetDetailedTask.getName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a detailed task from the list by index
     */
    public ReadOnlyDetailedTask getTask(int index) {
        return getListView().getItems().get(index);
    }

    public DetailedTaskCardHandle getDetailedTaskCardHandle(int index) {
        return getDetailedTaskCardHandle(new DetailedTask(getListView().getItems().get(index)));
    }

    public DetailedTaskCardHandle getDetailedTaskCardHandle(ReadOnlyDetailedTask detailedTask) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> detailedTaskCardNode = nodes.stream()
                .filter(n -> new DetailedTaskCardHandle(guiRobot, primaryStage, n).isSameTask(detailedTask))
                .findFirst();
        if (detailedTaskCardNode.isPresent()) {
            return new DetailedTaskCardHandle(guiRobot, primaryStage, detailedTaskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfDetailedTasks() {
        return getListView().getItems().size();
    }
}
