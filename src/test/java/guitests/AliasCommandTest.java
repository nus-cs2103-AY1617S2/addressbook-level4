package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.model.Task;

/**
 * Gui tests for alias command
 */
public class AliasCommandTest extends ToLuistGuiTest {
    @Test
    public void aliasAddCommand() {
        String aliasCommand = "alias d add";
        commandBox.runCommand(aliasCommand);

        String taskDescription = "read lecture slides from Uncle Soo";
        String addCommand = "d " + taskDescription;
        Task task = new Task(taskDescription);
        commandBox.runCommand(addCommand);
        assertTrue(isTaskShown(task));
    }
}
