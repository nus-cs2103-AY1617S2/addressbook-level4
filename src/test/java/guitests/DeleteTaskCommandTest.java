package guitests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.toluist.model.Task;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for delete task command
 */
public class DeleteTaskCommandTest extends ToLuistGuiTest {
    @Test
    public void deleteTask() {
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String command = "delete 1";
        commandBox.runCommand(command);
        assertFalse(isTaskShown(task));
    }
}
