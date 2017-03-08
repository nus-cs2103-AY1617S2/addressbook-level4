package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TestUtil;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for save command
 */
public class SaveCommandTest extends ToLuistGuiTest {
    @Test
    public void save() {
        String newPath = TestUtil.getFilePathInSandboxFolder("save_test.json");
        String command = "save " + newPath;
        commandBox.runCommand(command);

        // Check that storage path is changed
        assertEquals(TodoList.getStorage().getStoragePath(), newPath);

        // Check that todo list loaded is the same as previous todo list
        TodoList todoListAtSavedLocation = TodoList.load();
        assertEquals(todoListAtSavedLocation, new TypicalTestTodoLists().getTypicalTodoList());

    }
}
