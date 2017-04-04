//@@author A0131125Y
package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import seedu.toluist.TestApp;
import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.util.FileUtil;
import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TestUtil;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for getInstance command
 */
public class LoadCommandTest extends ToLuistGuiTest {
    @Test
    public void load_fileNotExists() {
        String newPath = TestUtil.getFilePathInSandboxFolder("load_test.json");
        FileUtil.removeFile(new File(newPath));
        String command = "load " + newPath;
        runCommandThenCheckForResultMessage(command,
                String.format(Messages.MESSAGE_SET_STORAGE_FAILURE, newPath));

        // Check that storage path is not changed
        assertEquals(Config.getInstance().getTodoListFilePath(), TestApp.SAVE_LOCATION_FOR_TESTING);
    }

    @Test
    public void load_fileExists() {
        String newPath = "./src/test/data/StorageTest/TypicalData.json";
        String command = "load " + newPath;
        runCommandThenCheckForResultMessage(command,
                String.format(Messages.MESSAGE_SET_STORAGE_SUCCESS, newPath));

        // Check that storage path is changed
        assertEquals(Config.getInstance().getTodoListFilePath(), newPath);

        // Check that todo list loaded is the same as previous todo list
        TodoList todoListAtLoadedLocation = TodoList.getInstance();
        assertTrue(TestUtil.compareTasksOfTodoLists(todoListAtLoadedLocation,
                new TypicalTestTodoLists().getTypicalTodoList()));
    }

    @Test
    public void load_sameLocation() {
        String newPath = TestApp.SAVE_LOCATION_FOR_TESTING;
        String command = "load " + newPath;
        runCommandThenCheckForResultMessage(command,
                String.format(Messages.MESSAGE_STORAGE_SAME_LOCATION, newPath));
    }

    @Test
    public void load_noStoragePath() {
        String command = "load ";
        runCommandThenCheckForResultMessage(command, Messages.MESSAGE_NO_STORAGE_PATH);
    }

    @Test
    public void load_invalidStoragePath() {
        String newPath = "sfas?////sffsf.json";
        String command = "load " + newPath;
        commandBox.runCommand(command);
        runCommandThenCheckForResultMessage(command,
                String.format(Messages.MESSAGE_SET_STORAGE_FAILURE, newPath));
    }

    @Test
    public void load_invalidData() {
        String newPath = "./src/test/data/StorageTest/NotJsonFormatData.json";
        String command = "load " + newPath;
        String.format(Messages.MESSAGE_SET_STORAGE_FAILURE,
                String.format(Messages.MESSAGE_SET_STORAGE_FAILURE, newPath));

        assertEquals(Config.getInstance().getTodoListFilePath(), TestApp.SAVE_LOCATION_FOR_TESTING);
    }
}
