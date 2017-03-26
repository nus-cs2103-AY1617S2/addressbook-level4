package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.LoadCommand.MESSAGE_FAILURE_DIRECTORY;
import static seedu.task.logic.commands.LoadCommand.MESSAGE_SUCCESS;
import static seedu.task.logic.commands.LoadCommand.MESSAGE_NOT_FOUND;

import java.io.File;

import org.junit.Test;

import seedu.task.testutil.TestUtil;

public class LoadCommandTest extends TaskManagerGuiTest {

    @Test
    public void save() {
        //loads a directory
        commandBox.runCommand("load data");
        assertResultMessage(String.format(MESSAGE_FAILURE_DIRECTORY, "data"));
        
        //loads a non-existing file
        commandBox.runCommand("load fileThatDoesntExist");
        assertResultMessage(String.format(MESSAGE_NOT_FOUND, "fileThatDoesntExist"));
        //saves to new file
        assertNewFileLoaded(TestUtil.getFilePathInSandboxFolder("sampleData.xml"));
        //resets config back to testdummy
        testApp.restartConfig();
    }

    /***
     * Loads the file and assert that it exists then deletes the file
     * @param location refers to the path of the file
     */
    public void assertNewFileLoaded(String location) {
        File target = new File(location);
        assertTrue(target.exists());
        commandBox.runCommand("load " + location);
        assertResultMessage(String.format(MESSAGE_SUCCESS, location));
    }

}
