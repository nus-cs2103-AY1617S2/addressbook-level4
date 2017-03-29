package guitests.guihandles;

import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Provides a handle to the Completed Panel of the app.
 */
public class CompletedTaskListHandle extends GuiHandle {

    private static final String COMPLETED_TASK_LIST_VIEW_ID = "#completedTaskListView";

    public CompletedTaskListHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public boolean isWindowOpen() {
        Node completedTaskList = guiRobot.lookup(COMPLETED_TASK_LIST_VIEW_ID).tryQuery().get();
        return (completedTaskList.getOpacity() == 1);
    }

    public ListView<ReadOnlyTask> getListView() {
        return getNode(COMPLETED_TASK_LIST_VIEW_ID);
    }

    public boolean isListMatching(ReadOnlyTask... tasks) {
        ObservableList<ReadOnlyTask> items = getListView().getItems();
        // Compare Size
        if (tasks.length != items.size()) {
            throw new IllegalArgumentException(
                    "List size mismatched\n" + "Expected " + (getListView().getItems().size() - 1) + " tasks");
        }
        // Compare Elements
        for (int i = 0; i < items.size(); i++) {
            if (!tasks[i].isSameStateAs(items.get(i))) {
                return false;
            }
        }

        return true;
    }

}
