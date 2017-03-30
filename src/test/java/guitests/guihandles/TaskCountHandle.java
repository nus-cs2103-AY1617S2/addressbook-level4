//@@author A0142255M

package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.tache.TestApp;

/**
 * A handler for the TaskCount of the UI
 */
public class TaskCountHandle extends GuiHandle {

    public static final String TIMED_TASK_COUNT_ID = "#timedTaskCount";
    public static final String FLOATING_TASK_COUNT_ID = "#floatingTaskCount";

    public TaskCountHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public Label getTimedTaskCount() {
        return getNode(TIMED_TASK_COUNT_ID);
    }

    public Label getFloatingTaskCount() {
        return getNode(FLOATING_TASK_COUNT_ID);
    }
}
