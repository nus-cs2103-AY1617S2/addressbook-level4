package guitests.guihandles;

import java.util.Arrays;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.onetwodo.TestApp;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends GuiHandle {
    public MainMenuHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public GuiHandle clickOn(String... menuText) {
        Arrays.stream(menuText).forEach((menuItem) -> guiRobot.clickOn(menuItem));
        return this;
    }

    public HelpWindowHandle openHelpWindowUsingMenu() {
        clickOn("Help", "Help", "F1");
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    public HelpUGWindowHandle openHelpUGWindowUsingMenu() {
        clickOn("Help", "Help", "F2");
        return new HelpUGWindowHandle(guiRobot, primaryStage);
    }

    public HelpWindowHandle openHelpWindowUsingAccelerator() {
        useAccelerator(KeyCode.F1);
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    public HelpUGWindowHandle openHelpUGWindowUsingAccelerator() {
        useAccelerator(KeyCode.F2);
        return new HelpUGWindowHandle(guiRobot, primaryStage);
    }

    private void useAccelerator(KeyCode code) {
        guiRobot.push(code);
        guiRobot.sleep(500);
    }

    public void executeUndoWithMenu() {
        clickOn("Edit", "Undo");
    }

    public void executeRedoWithMenu() {
        clickOn("Edit", "Redo");
    }
}
