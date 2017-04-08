package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.tache.TestApp;

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

    public CommandBoxHandle getCommandBox() {
        return new CommandBoxHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(guiRobot, primaryStage);
    }

    public CalendarPanelHandle getCalendarPanel() {
        return new CalendarPanelHandle(guiRobot, primaryStage);
    }

    //@@author A0142255M
    public TaskListTypeHandle getTaskListType() {
        return new TaskListTypeHandle(guiRobot, primaryStage);
    }

    public TaskCountHandle getTaskCount() {
        return new TaskCountHandle(guiRobot, primaryStage);
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return new StatusBarFooterHandle(guiRobot, primaryStage);
    }
    //@@author

    //@@author A0139925U
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public MainGuiHandle toggleMainGuiUsingCtrlAltDAccelerator() {
        useCtrlAltDAccelerator();
        return new MainGuiHandle(guiRobot, primaryStage);
    }

    public MainGuiHandle toggleMainGuiUsingCtrlQAccelerator() {
        useCtrlQAccelerator();
        return new MainGuiHandle(guiRobot, primaryStage);
    }

    private void useCtrlAltDAccelerator() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.ALT, KeyCode.D);
        guiRobot.sleep(2000);
    }

    private void useCtrlQAccelerator() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.Q);
        guiRobot.sleep(2000);
    }
    //@@author

    public AlertDialogHandle getAlertDialog(String title) {
        guiRobot.sleep(1000);
        return new AlertDialogHandle(guiRobot, primaryStage, title);
    }
}
