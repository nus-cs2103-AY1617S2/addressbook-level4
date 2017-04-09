package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


// @@author A0163845X
public class PathCommandTest extends TaskManagerGuiTest {

    @Test
    public void path() {
        try {
            setup();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        commandBox.runCommand("showcase 75");
        commandBox.runCommand("path TaskManager1.xml");
        commandBox.runCommand("showcase 50");
        commandBox.runCommand("load TaskManager.xml");
        assertTrue(taskListPanel.getNumberOfTasks() == 75);
        commandBox.runCommand("load TaskManager1.xml");
        assertTrue(taskListPanel.getNumberOfTasks() == 50);
        commandBox.runCommand("load TaskManager.xml");
        // @@author
    }
}
