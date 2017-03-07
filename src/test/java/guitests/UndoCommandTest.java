package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.model.Task;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for delete task command
 */
public class UndoCommandTest extends GuiTest {
    @Test
    public void undoSingleCommand() {
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String deleteCommand = "delete 1";
        String undoCommand = "undo";

        commandBox.runCommand(deleteCommand);
        commandBox.runCommand(undoCommand);

        assertTrue(isTaskShown(task));
    }
}
