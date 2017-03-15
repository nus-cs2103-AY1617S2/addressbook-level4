package guitests;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.TestApp;
import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.util.FileUtil;
import seedu.toluist.controller.StoreController;
import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TestUtil;
import seedu.toluist.testutil.TypicalTestTodoLists;

import java.io.File;
import java.io.IOException;

/**
 * Gui tests for saveConfig command
 */
public class SaveCommandTest extends ToLuistGuiTest {
    @Test
    public void save_fileNotExists() {
        String newPath = TestUtil.getFilePathInSandboxFolder("save_test.json");
        FileUtil.removeFile(new File(newPath));
        String command = "save " + newPath;
        commandBox.runCommand(command);

        // Check that storage path is changed
        assertEquals(Config.getInstance().getTodoListFilePath(), newPath);

        // Check that todo list loaded is the same as previous todo list
        TodoList todoListAtSavedLocation = TodoList.load();
        assertTrue(TestUtil.compareTasksOfTodoLists(todoListAtSavedLocation,
                                                    new TypicalTestTodoLists().getTypicalTodoList()));
        assertResultMessage(String.format(StoreController.RESULT_MESSAGE_SUCCESS, newPath));
    }

    @Test
    public void save_fileExists() {
        String newPath = TestUtil.getFilePathInSandboxFolder("existing_file.json");
        try {
            FileUtil.createFile(new File(newPath));
            String command = "save " + newPath;
            commandBox.runCommand(command);

            // Check that storage path is changed
            assertEquals(Config.getInstance().getTodoListFilePath(), newPath);

            // Check that todo list loaded is the same as previous todo list
            TodoList todoListAtSavedLocation = TodoList.load();
            assertTrue(TestUtil.compareTasksOfTodoLists(todoListAtSavedLocation,
                    new TypicalTestTodoLists().getTypicalTodoList()));
            assertResultMessage(String.format(StoreController.RESULT_MESSAGE_WARNING_OVERWRITE, newPath)
                + "\n" + String.format(StoreController.RESULT_MESSAGE_SUCCESS, newPath));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void save_sameLocation() {
        String newPath = TestApp.SAVE_LOCATION_FOR_TESTING;
        String command = "save " + newPath;
        commandBox.runCommand(command);

        assertResultMessage(String.format(StoreController.RESULT_MESSAGE_SAME_LOCATION, newPath));
    }
}
