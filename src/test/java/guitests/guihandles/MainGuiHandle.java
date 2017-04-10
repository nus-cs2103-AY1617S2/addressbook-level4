package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.taskmanager.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public EventTaskListPanelHandle getEventTaskListPanel() {
        return new EventTaskListPanelHandle(guiRobot, primaryStage);
    }

    public DeadlineTaskListPanelHandle getDeadlineTaskListPanel() {
        return new DeadlineTaskListPanelHandle(guiRobot, primaryStage);
    }

    public FloatingTaskListPanelHandle getFloatingTaskListPanel() {
        return new FloatingTaskListPanelHandle(guiRobot, primaryStage);
    }

    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle(guiRobot, primaryStage);
    }

    public CommandBoxHandle getCommandBox() {
        return new CommandBoxHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(guiRobot, primaryStage);
    }

    public AlertDialogHandle getAlertDialog(String title) {
        guiRobot.sleep(1000);
        return new AlertDialogHandle(guiRobot, primaryStage, title);
    }
}
