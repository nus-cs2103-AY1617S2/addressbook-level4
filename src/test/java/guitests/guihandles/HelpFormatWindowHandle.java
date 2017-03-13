package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * Provides a handle to the help summary window of the app.
 */
public class HelpFormatWindowHandle extends GuiHandle {

    private static final String HELP_WINDOW_TITLE = "Command Summary";
    private static final String HELP_WINDOW_ROOT_FIELD_ID = "#helpWindowRoot";

    public HelpFormatWindowHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, HELP_WINDOW_TITLE);
        guiRobot.sleep(1000);
    }

    public boolean isWindowOpen() {
        return guiRobot.lookup(HELP_WINDOW_ROOT_FIELD_ID).tryQuery().isPresent();
    }

    public void closeWindow() {
        super.closeWindow();
        guiRobot.sleep(500);
    }

}
