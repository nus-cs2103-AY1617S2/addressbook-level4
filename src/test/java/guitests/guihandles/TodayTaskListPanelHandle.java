package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import t09b1.today.TestApp;
import t09b1.today.model.task.ReadOnlyTask;

/**
 * Provides a handle for the Today panel containing the task list.
 */
public class TodayTaskListPanelHandle extends MainListGuiHandle {

    private static final String FUTURE_TASK_LIST_VIEW_ID = "#todayTaskListView";

    public TodayTaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    @Override
    public ListView<ReadOnlyTask> getListView() {
        return getNode(FUTURE_TASK_LIST_VIEW_ID);
    }

}
