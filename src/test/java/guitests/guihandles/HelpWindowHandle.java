package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.onetwodo.TestApp;

/**
 * Provides a handle to the help window of the app.
 */
public class HelpWindowHandle extends GuiHandle {

    public HelpWindowHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
        guiRobot.sleep(1000);
    }

    @Override
    public void closeWindow() {
        guiRobot.push(KeyCode.ESCAPE);
        guiRobot.sleep(500);
    }

}
