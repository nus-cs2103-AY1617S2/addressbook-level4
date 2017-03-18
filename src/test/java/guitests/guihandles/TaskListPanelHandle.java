package guitests.guihandles;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.testutil.TestUtil;

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
        Node[] nodes = (Node[]) getAllCardNodes().toArray();
        ArrayList<TaskCardHandle> taskCards = new ArrayList<TaskCardHandle>();
        for (int i = 0;  i < nodes.length;  i++) {
            taskCards.add(new TaskCardHandle(guiRobot, primaryStage, nodes[i]));
        }
        return taskCards;
    }

    /**
     * Returns true if the list is showing the task details correctly, regardless of the order.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... tasks) throws IllegalArgumentException {
        TaskCardHandle[] taskCards = (TaskCardHandle[]) getTaskCards().toArray();
        if (tasks.length != taskCards.length) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + taskCards.length + " tasks");
        }
        for (int i = 0; i < tasks.length; i++) {
            boolean found = false;
            for (int j = 0;  j < taskCards.length;  j++) {
                if (TestUtil.compareCardAndTask(taskCards[j], tasks[i])) {
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

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTasks() {
        return getTaskCards().size();
    }
}
