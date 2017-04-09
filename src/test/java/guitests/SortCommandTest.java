package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;



// @@author A0163845X
public class SortCommandTest extends TaskManagerGuiTest {

    @Test
    public void sort() {
        try {
            setup();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        commandBox.runCommand("showcase 75");
        //filter name impossible
        commandBox.runCommand("sort name");
        for (int i = 0; i < taskListPanel.getNumberOfTasks() - 1; i++) {
            assertTrue(taskListPanel.getTask(i).getTaskName()
                    .compareTo(taskListPanel.getTask(i + 1).getTaskName()) <= 0);
        }
        commandBox.runCommand("showcase 75");
        commandBox.runCommand("sort time");
        for (int i = 0; i < taskListPanel.getNumberOfTasks() - 1; i++) {
            assertTrue((taskListPanel.getTask(i).getTaskDate() == null
                    || taskListPanel.getTask(i + 1).getTaskDate() == null)
                    ||taskListPanel.getTask(i).getTaskDate()
                    .compareTo(taskListPanel.getTask(i + 1).getTaskDate()) >= 0);
        }
        commandBox.runCommand("showcase 75");
        commandBox.runCommand("sort status");
        for (int i = 0; i < taskListPanel.getNumberOfTasks() - 1; i++) {
            assertTrue(!(taskListPanel.getTask(i).getTaskStatus().equals("Completed")
                    && taskListPanel.getTask(i + 1).getTaskStatus().equals("Ongoing")));
        }
        // @@author
    }
}
