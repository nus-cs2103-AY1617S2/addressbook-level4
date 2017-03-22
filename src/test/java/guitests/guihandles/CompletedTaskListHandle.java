package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.TestApp;

/**
 * Provides a handle to the help window of the app.
 */
public class CompletedTaskListHandle extends GuiHandle {

    private static final String COMPLETED_TASK_LIST_FIELD_ID = "#completedTaskListView";

    public CompletedTaskListHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public boolean isWindowOpen() {
        Node completedTaskList = guiRobot.lookup(COMPLETED_TASK_LIST_FIELD_ID).tryQuery().get();
        return (completedTaskList.getOpacity() == 1);
    }

}
