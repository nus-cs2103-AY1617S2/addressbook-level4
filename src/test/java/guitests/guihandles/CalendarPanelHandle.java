package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.tache.TestApp;

/**
 * A handler for the CalendarPanel of the UI
 */
public class CalendarPanelHandle extends GuiHandle {

    private static final String CALENDAR_ID = "#calendar";

    public CalendarPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Clicks on the WebView.
     */
    public void clickOnWebView() {
        guiRobot.clickOn(CALENDAR_ID);
    }

}
