package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.onetwodo.TestApp;

//@@author A0141138N
/**
 * Provides a handle to the help window of the app.
 */
public class HelpUGWindowHandle extends GuiHandle {

    public HelpUGWindowHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
        guiRobot.sleep(1000);
    }

    @Override
    public void closeWindow() {
        guiRobot.push(KeyCode.ESCAPE);
        guiRobot.sleep(500);
    }

}
