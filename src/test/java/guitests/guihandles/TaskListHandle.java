//@@author A0131125Y
package guitests.guihandles;

import guitests.GuiRobot;

import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.toluist.TestApp;
import seedu.toluist.model.Task;

/**
 * Provides a handle for the TaskList.
 */
public class TaskListHandle extends GuiHandle {
    public static final String TASK_LIST_VIEW_ID = "#taskListView";

    public TaskListHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public ListView<Task> getTaskList() {
        return (ListView<Task>) getNode(TASK_LIST_VIEW_ID);
    }
}
