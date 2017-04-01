//@@author A0131125Y
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.toluist.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {
    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public TaskListHandle getTaskList() {
        return new TaskListHandle(guiRobot, primaryStage);
    }

    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle(guiRobot, primaryStage);
    }

    public CommandBoxHandle getCommandBox() {
        return new CommandBoxHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public TabBarViewHandle getTabBar() {
        return new TabBarViewHandle(guiRobot, primaryStage);
    }

    public CommandAutoCompleteViewHandle getCommandAutoCompleteView() {
        return new CommandAutoCompleteViewHandle(guiRobot, primaryStage);
    }

    public HelpHandle getHelpView() {
        return new HelpHandle(guiRobot, primaryStage);
    }
}
