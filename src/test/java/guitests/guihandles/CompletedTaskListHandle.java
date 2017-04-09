package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.today.TestApp;
import seedu.today.model.task.ReadOnlyTask;

/**
 * Provides a handle to the Completed Panel of the app.
 */
public class CompletedTaskListHandle extends MainListGuiHandle {

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

}
