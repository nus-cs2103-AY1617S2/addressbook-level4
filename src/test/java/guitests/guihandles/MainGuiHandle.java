package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.address.TestApp;

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

	public ResultDisplayHandle getResultDisplay() {
		return new ResultDisplayHandle(this.guiRobot, this.primaryStage);
	}

	public CommandBoxHandle getCommandBox() {
		return new CommandBoxHandle(this.guiRobot, this.primaryStage, TestApp.APP_TITLE);
	}

	public MainMenuHandle getMainMenu() {
		return new MainMenuHandle(this.guiRobot, this.primaryStage);
	}

	public BrowserPanelHandle getBrowserPanel() {
		return new BrowserPanelHandle(this.guiRobot, this.primaryStage);
	}

	public AlertDialogHandle getAlertDialog(String title) {
		this.guiRobot.sleep(1000);
		return new AlertDialogHandle(this.guiRobot, this.primaryStage, title);
	}
}
