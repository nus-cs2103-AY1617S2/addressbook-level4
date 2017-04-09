package guitests.guihandles;

import java.util.Arrays;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.doit.TestApp;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends GuiHandle {
    public MainMenuHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public GuiHandle clickOn(String... menuText) {
        Arrays.stream(menuText).forEach((menuItem) -> this.guiRobot.clickOn(menuItem));
        return this;
    }

    public HelpWindowHandle openHelpWindowUsingMenu() {
        clickOn("Help", "F1");
        return new HelpWindowHandle(this.guiRobot, this.primaryStage);
    }

    public HelpWindowHandle openHelpWindowUsingAccelerator() {
        useF1Accelerator();
        return new HelpWindowHandle(this.guiRobot, this.primaryStage);
    }

    private void useF1Accelerator() {
        this.guiRobot.push(KeyCode.F1);
        this.guiRobot.sleep(500);
    }

    // @@author A0138909R
    public void useCTRLZAccelerator() {
        this.guiRobot.push(KeyCode.CONTROL, KeyCode.Z);
        this.guiRobot.sleep(500);
    }

    public void useCTRLYAccelerator() {
        this.guiRobot.push(KeyCode.CONTROL, KeyCode.Y);
        this.guiRobot.sleep(500);
    }
    // @@author
}
