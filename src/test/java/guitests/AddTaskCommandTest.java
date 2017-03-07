package guitests;

import org.junit.Test;

import seedu.toluist.model.Task;

import static org.junit.Assert.assertTrue;

/**
 * Gui tests for add task command
 */
public class AddTaskCommandTest extends GuiTest {
    @Test
    public void addFloatingTask() {
        String taskDescription = "do homework for Melvin";
        String command = "add " + taskDescription;
        Task task = new Task(taskDescription);
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }
}
