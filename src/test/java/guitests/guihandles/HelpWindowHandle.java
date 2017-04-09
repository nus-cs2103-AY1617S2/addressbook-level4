package guitests.guihandles;

import guitests.GuiRobot;
import guitests.TaskBookGuiTest;
import javafx.stage.Stage;

/**
 * Provides a handle to the help window of the app.
 */
public class HelpWindowHandle extends GuiHandle {

    private static final String HELP_WINDOW_TITLE = "Help";
    private static final String HELP_WINDOW_ROOT_FIELD_ID = "#helpWindowRoot";

    public HelpWindowHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, HELP_WINDOW_TITLE);
        guiRobot.sleep(10 * TaskBookGuiTest.SLEEP);
    }

    public boolean isWindowOpen() {
        return guiRobot.lookup(HELP_WINDOW_ROOT_FIELD_ID).tryQuery().isPresent();
    }

    public void closeWindow() {
        super.closeWindow();
        guiRobot.sleep(5 * TaskBookGuiTest.SLEEP);
    }

}
