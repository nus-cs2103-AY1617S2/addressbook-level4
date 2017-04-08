package guitests.guihandles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.testutil.TestUtil;
import seedu.task.TestApp;

/**
 * Provides a handle for the panel containing the task list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<TaskCardHandle> getTaskCards() {
        Set<Node> nodes = getAllCardNodes();
        ArrayList<TaskCardHandle> taskCards = new ArrayList<TaskCardHandle>();
        for (Node node : nodes) {
            taskCards.add(new TaskCardHandle(guiRobot, primaryStage, node));
        }
        return taskCards;
    }

    /**
     * Returns true if the list is showing the task details correctly, regardless of the order.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... tasks) throws IllegalArgumentException {
        List<TaskCardHandle> taskCards = getTaskCards();
        if (tasks.length != taskCards.size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + taskCards.size() + " tasks");
        }
        for (ReadOnlyTask task : tasks) {
            boolean found = false;
            for (TaskCardHandle taskCard : taskCards) {
                if (TestUtil.compareCardAndTask(taskCard, task)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public TaskCardHandle getTaskCardWithID(int id) {
        List<TaskCardHandle> taskCards = getTaskCards();
        for (TaskCardHandle taskCard : taskCards) {
            // TODO: remove this close bracket in id
            if (taskCard.getID().equals(id + ") ")) {
                return taskCard;
            }
        }
        return null;
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTasks() {
        return getTaskCards().size();
    }
}
