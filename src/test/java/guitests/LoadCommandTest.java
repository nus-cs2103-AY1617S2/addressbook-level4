package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.logic.commands.LoadCommand;
import seedu.tasklist.testutil.TestUtil;
//@@author A0141993X
public class LoadCommandTest extends TaskListGuiTest {
    private static String newFilePath = TestUtil.getFilePathInSandboxFolder("sampleData.xml");

    @Test
    public void load_invalidCommand_errorMessageShown() {
        String command = "loads data/taskList.xml";
        commandBox.runCommand(command);
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void load_invalidExtension_errorMessageShown() {
        String command = "load data/taskList.doc";
        commandBox.runCommand(command);
        assertResultMessage(String.format(LoadCommand.MESSAGE_INVALID_PATH, "data/taskList.doc"));
    }

    @Test
    public void load_CorrectExtension_success() {
        String command = "load " + newFilePath;
        commandBox.runCommand(command);
        assertResultMessage(String.format(LoadCommand.MESSAGE_SUCCESS, newFilePath));
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
    }

    @Test
    public void load_NoSuchFileCorrectExtension_fail() {
        String command = "load data/mytaskList.xml";
        commandBox.runCommand(command);
        assertResultMessage(String.format(LoadCommand.MESSAGE_FAILURE, "data/mytaskList.xml"));
    }

}
