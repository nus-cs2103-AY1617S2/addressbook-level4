package guitests.guihandles;

import java.util.Arrays;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.task.TestApp;

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
        clickOn("Help", "F1");
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    public HelpWindowHandle openHelpWindowUsingAccelerator() {
        useF1Accelerator();
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    public HelpFormatWindowHandle openHelpFormatWindowUsingMenu() {
        clickOn("Help", "F3");
        return new HelpFormatWindowHandle(guiRobot, primaryStage);
    }

    public HelpFormatWindowHandle openHelpFormatWindowUsingAccelerator() {
        useF3Accelerator();
        return new HelpFormatWindowHandle(guiRobot, primaryStage);
    }

    private void useF1Accelerator() {
        guiRobot.push(KeyCode.F1);
        guiRobot.sleep(500);
    }

    private void useF3Accelerator() {
        guiRobot.push(KeyCode.F3);
        guiRobot.sleep(500);
    }

    public void useCtrlAltA() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.ALT, KeyCode.A);
        guiRobot.sleep(500);
    }

    public void useCtrlAltZ() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.ALT, KeyCode.Z);
        guiRobot.sleep(500);
    }

    public void useCtrlAltD() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.ALT, KeyCode.D);
        guiRobot.sleep(500);
    }

    public void useCtrlAltE() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.ALT, KeyCode.E);
        guiRobot.sleep(500);
    }

    public void useCtrlAltS() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.ALT, KeyCode.S);
        guiRobot.sleep(500);
    }

    public void useCtrlS() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.S);
        guiRobot.sleep(500);
    }

    public void useCtrlAltL() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.ALT, KeyCode.L);
        guiRobot.sleep(500);
    }

    public void useShiftDown() {
        guiRobot.push(KeyCode.SHIFT, KeyCode.DOWN);
        guiRobot.sleep(500);
    }

    public void useShiftUp() {
        guiRobot.push(KeyCode.SHIFT, KeyCode.UP);
        guiRobot.sleep(500);
    }
}
