package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.model.Task;

/**
 * Gui tests for add task command
 */
public class AddTaskCommandTest extends ToLuistGuiTest {
    @Test
    public void addFloatingTask() {
        // add one task
        String taskDescription = "do homework for Melvin";
        String command = "add " + taskDescription;
        Task task = new Task(taskDescription);
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));

        // add another task
        String taskDescription2 = "drink Koi after school";
        String command2 = "add " + taskDescription2;
        Task task2 = new Task(taskDescription2);
        commandBox.runCommand(command2);
        assertTrue(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }
}
