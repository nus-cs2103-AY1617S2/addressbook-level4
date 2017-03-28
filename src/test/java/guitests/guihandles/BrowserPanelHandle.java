package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.todolist.TestApp;

/**
 * A handler for the BrowserPanel of the UI
 */
public class BrowserPanelHandle extends GuiHandle {

    private static final String TASKDETAILS_ID = "#taskDetails";

    public BrowserPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Clicks on the WebView.
     */
    public void clickOnWebView() {
        guiRobot.clickOn(TASKDETAILS_ID);
    }

}
