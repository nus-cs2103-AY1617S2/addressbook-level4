package guitests;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;

public class ViewCommandTest extends TaskManagerGuiTest {

    protected final String TASK_LIST_FXML_ID = "#taskListView";

    @Test
    public void viewDefault() {
        assertGroupsDisplay("All");
    }

    @Test
    public void viewCalendar() {
        commandBox.runCommand("view calendar");
        assertGroupsDisplay("Floating", "Overdue", "Today", "Tomorrow", "Future");
    }

    @Test
    public void viewGroups() {
        commandBox.runCommand("view done today tomorrow");
        assertGroupsDisplay("Done", "Today", "Tomorrow");
    }

    protected void assertGroupsDisplay(String... groupTitles) {
        Node taskListView = mainGui.getNodeWithID(TASK_LIST_FXML_ID);
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
