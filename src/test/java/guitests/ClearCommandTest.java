package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.model.Task;

/**
 * Gui tests for clear command
 */
public class ClearCommandTest extends ToLuistGuiTest {

    @Test
    public void clearCurrentTasks() {
        String command = "clear";
        commandBox.runCommand(command);
        assertTrue(getTasksShown().isEmpty());

    }

    @Test
    public void addAndRemoveATask() {
        String taskDescription = "drink Koi after school";
        String command = "add " + taskDescription;
        String command2 = "clear";

        commandBox.runCommand(command);
        commandBox.runCommand(command2);

        Task task = new Task(taskDescription);
        assertFalse(isTaskShown(task));
    }
}
