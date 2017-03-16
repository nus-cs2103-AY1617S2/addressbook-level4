package guitests;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import seedu.toluist.TestApp;
import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.util.FileUtil;
import seedu.toluist.controller.StoreController;
import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TestUtil;
import seedu.toluist.testutil.TypicalTestTodoLists;

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
        assertResultMessage(String.format(Messages.MESSAGE_SET_STORAGE_SUCCESS, newPath));
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
                + "\n" + String.format(Messages.MESSAGE_SET_STORAGE_SUCCESS, newPath));
        } catch (IOException e) {
            fail("Should not reach here");
        }
    }

    @Test
    public void save_sameLocation() {
        String newPath = TestApp.SAVE_LOCATION_FOR_TESTING;
        String command = "save " + newPath;
        commandBox.runCommand(command);

        assertResultMessage(String.format(Messages.MESSAGE_STORAGE_SAME_LOCATION, newPath));
    }

    @Test
    public void save_noStoragePath() {
        String command = "save ";
        commandBox.runCommand(command);

        assertResultMessage(Messages.MESSAGE_NO_STORAGE_PATH);
    }

    @Test
    public void save_invalidStoragePath() {
        String newPath = "///invalidLocation????/a";
        String command = "save " + newPath;
        commandBox.runCommand(command);

        assertResultMessage(String.format(Messages.MESSAGE_SET_STORAGE_FAILURE, newPath));
    }
}
