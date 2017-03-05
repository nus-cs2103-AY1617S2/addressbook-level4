package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import seedu.toluist.TestApp;
import seedu.toluist.model.Task;


/**
 * Provides a handle for the TaskList.
 */
public class TaskListHandle extends GuiHandle {
    private static final String TASK_LIST_VIEW_ID = "#TasksListView";

    public TaskListHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public ListView<Task> getTaskList() {
        return (ListView<Task>) getNode(TASK_LIST_VIEW_ID);
    }
}
