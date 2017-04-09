//@@author A0162011A
package guitests.guihandles;

import guitests.GuiRobot;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.toluist.TestApp;

/**
 * Provides a handle to the help view of the app.
 */
public class HelpHandle extends GuiHandle {
    private static final String HELP_CONTAINER_ID = "#helpPlaceholder";
    private static final String HELP_HEADING_ID = "#headerLabel";
    private static final String HELP_LIST_VIEW_ID = "#helpListView";

    public HelpHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public boolean isVisible() {
        return getNode(HELP_CONTAINER_ID).isVisible();
    }

    public String getHeading() {
        return ((Label) getNode(HELP_HEADING_ID)).getText();
    }
}
