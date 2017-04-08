//@@author A0163935X
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
/**
 * A handle to the Command Box in the GUI.
 */
public class CalenderPanelHandle extends GuiHandle {

    private static final String PERSON_LIST_VIEW_ID = "#listview";

    public CalenderPanelHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }

    /**
     * Clicks on the TextField.
     */
    public ListView<String> getListView(int i) {
        return getNode(PERSON_LIST_VIEW_ID + i);
    }

    public String getDateOfIthLabel(int i) {
        return getLabelText("#day" + i);
    }

}
