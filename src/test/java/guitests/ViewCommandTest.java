//@@author A0144885R
package guitests;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;

import seedu.address.logic.commands.ViewCommand;

public class ViewCommandTest extends TaskManagerGuiTest {

    protected final String taskListFxamlId = "#taskListView";

    @Test
    public void viewDefault() {
        assertGroupsDisplay("Done", "Unfinished");
    }

    @Test
    public void viewCalendar() {
        commandBox.runCommand("view calendar");
        assertGroupsDisplay("Floating", "Overdue", "Today", "Tomorrow", "Future");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, "Calendar"));
    }

    @Test
    public void viewGroups() {
        commandBox.runCommand("view done today tomorrow");
        assertGroupsDisplay("Done", "Today", "Tomorrow");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, "Done|Today|Tomorrow"));
    }

    @Test
    public void viewWrongInput() {
        commandBox.runCommand("view randomstring");
        assertGroupsDisplay("Done", "Unfinished");
        assertResultMessage(ViewCommand.MESSAGE_ERROR);
    }

    protected void assertGroupsDisplay(String... groupTitles) {
        Node taskListView = mainGui.getNodeWithID(taskListFxamlId);
        Set<Node> taskGroupNodes = taskListView.lookupAll("#titledPane");

        int index = 0;
        for (Node node : taskGroupNodes) {
            TitledPane titledPane = (TitledPane) node;
            // Title consists of title + no. of entries
            // e.g. Tomorrow (4)
            String title = titledPane.getText().split(" ")[0];
            assertEquals(title, groupTitles[index]);
            index++;
        }
    }

}
