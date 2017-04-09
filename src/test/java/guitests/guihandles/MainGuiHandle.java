package guitests.guihandles;

import guitests.GuiRobot;
import guitests.TaskBookGuiTest;
import javafx.stage.Stage;
import seedu.jobs.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public TaskListPanelHandle getTaskListPanel() {
        return new TaskListPanelHandle(guiRobot, primaryStage);
    }

    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle(guiRobot, primaryStage);
    }

    public CommandBoxHandle getCommandBox() throws InterruptedException {
        return new CommandBoxHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(guiRobot, primaryStage);
    }

    public AlertDialogHandle getAlertDialog(String title) {
        guiRobot.sleep(10 * TaskBookGuiTest.SLEEP);
        return new AlertDialogHandle(guiRobot, primaryStage, title);
    }
}
