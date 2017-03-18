package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.doit.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public TaskListPanelHandle getTaskListPanel() {
        return new TaskListPanelHandle(this.guiRobot, this.primaryStage);
    }

    public EventListPanelHandle getEventListPanel() {
        return new EventListPanelHandle(this.guiRobot, this.primaryStage);
    }

    public FloatingTaskListPanelHandle getFloatingTaskListPanel() {
        return new FloatingTaskListPanelHandle(this.guiRobot, this.primaryStage);
    }

    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle(this.guiRobot, this.primaryStage);
    }

    public CommandBoxHandle getCommandBox() {
        return new CommandBoxHandle(this.guiRobot, this.primaryStage, TestApp.APP_TITLE);
    }

    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(this.guiRobot, this.primaryStage);
    }
}
