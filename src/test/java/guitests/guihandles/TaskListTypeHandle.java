//@@author A0142255M

package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.tache.TestApp;

/**
 * A handler for the TaskListType of the UI
 */
public class TaskListTypeHandle extends GuiHandle {

    public static final String TASK_LIST_TYPE_ID = "#taskListType";

    public TaskListTypeHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public Label getTaskListType() {
        return getNode(TASK_LIST_TYPE_ID);
    }
}
