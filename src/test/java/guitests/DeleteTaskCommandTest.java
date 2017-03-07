package guitests;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

import seedu.toluist.model.Task;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for delete task command
 */
public class DeleteTaskCommandTest extends GuiTest {
    @Test
    public void deleteTask() {
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String command = "delete 1";
        commandBox.runCommand(command);
        assertFalse(isTaskShown(task));
    }
}
