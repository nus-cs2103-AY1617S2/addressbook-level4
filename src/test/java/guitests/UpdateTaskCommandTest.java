package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.model.Task;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for update task command
 */
public class UpdateTaskCommandTest extends GuiTest {
    @Test
    public void updateTaskDescription() {
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String newDescription = "do homework for Melvin";
        String command = "update 1 " + newDescription;
        commandBox.runCommand(command);
        assertFalse(isTaskShown(new TypicalTestTodoLists().getTypicalTasks()[0]));
        assertTrue(isTaskShown(task));
    }
}
