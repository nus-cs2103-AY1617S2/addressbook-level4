package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.jobs.commons.core.Messages;
import seedu.jobs.testutil.TestTask;

public class FindCommandTest extends TaskBookGuiTest {

/*    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Final exam"); // no results
        assertFindResult("find Lab", td.CS3102, td.CS3104); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Lab", td.CS3104);
    }*/

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find ST2131"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findFIN3131");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
