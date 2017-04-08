package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.today.TestApp;
import seedu.today.model.task.ReadOnlyTask;

/**
 * Provides a handle for the Future panel containing the task list.
 */
public class FutureTaskListPanelHandle extends MainListGuiHandle {

    private static final String FUTURE_TASK_LIST_VIEW_ID = "#futureTaskListView";

    public FutureTaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    @Override
    public ListView<ReadOnlyTask> getListView() {
        return getNode(FUTURE_TASK_LIST_VIEW_ID);
    }

}
