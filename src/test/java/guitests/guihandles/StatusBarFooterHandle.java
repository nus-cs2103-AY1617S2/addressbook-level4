//@@author A0142255M

package guitests.guihandles;

import org.controlsfx.control.StatusBar;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.tache.TestApp;

/**
 * A handler for the StatusBarFooter of the UI
 */
public class StatusBarFooterHandle extends GuiHandle {

    public static final String SYNC_STATUS_ID = "#syncStatus";
    public static final String SAVE_LOCATION_STATUS_ID = "saveLocationStatus";

    public StatusBarFooterHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public StatusBar getSyncStatus() {
        return getNode(SYNC_STATUS_ID);
    }

    public StatusBar getSaveLocationStatus() {
        return getNode(SAVE_LOCATION_STATUS_ID);
    }
}
