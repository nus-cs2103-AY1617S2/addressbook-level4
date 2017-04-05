package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

//@@author A0142487Y
public class FindExactCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {

        assertFindExactResult("fe buy pENcil", td.buy); // single result
        assertFindExactResult("findexact Give present", td.give); // single result
        assertFindExactResult("fe Mark"); // no results
        assertFindExactResult("finde apply", td.apply); // single result

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindExactResult("fexact apply"); // no result
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindExactResult("findexact Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findexactgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindExactResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
