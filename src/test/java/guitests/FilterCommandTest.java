package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.TaskDate;

// @@author A0163845X
public class FilterCommandTest extends TaskManagerGuiTest {

    @Test
    public void filter() {
        try {
            setup();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        commandBox.runCommand("clear");
        commandBox.runCommand("showcase 75");
        // filter name impossible
        commandBox.runCommand("filter name impossibleName");
        assertTrue(0 == taskListPanel.getNumberOfTasks());

        commandBox.runCommand("list");
        commandBox.runCommand("filter name ore");
        for (int i = 0; i < taskListPanel.getNumberOfTasks(); i++) {
            // System.out.println(taskListPanel.getTask(i).getTaskName().fullTaskName);
            assertTrue(taskListPanel.getTask(i).getTaskName().fullTaskName.equals("Go to store"));
        }
        commandBox.runCommand("list");
        commandBox.runCommand("filter before 060617");
        try {
            TaskDate date = new TaskDate("060617");
            for (int i = 0; i < taskListPanel.getNumberOfTasks(); i++) {
                assertTrue(taskListPanel.getTask(i).getTaskDate().compareTo(date) <= 0);
            }
            commandBox.runCommand("list");
            commandBox.runCommand("filter after 060617");
            for (int i = 0; i < taskListPanel.getNumberOfTasks(); i++) {
                assertTrue(taskListPanel.getTask(i).getTaskDate().compareTo(date) >= 0);
            }
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        commandBox.runCommand("list");
        commandBox.runCommand("filter desc century");
        for (int i = 0; i < taskListPanel.getNumberOfTasks(); i++) {
            assertTrue(taskListPanel.getTask(i).getTaskName().fullTaskName.equals("Go to store"));
        }
        // @@author
    }
}
