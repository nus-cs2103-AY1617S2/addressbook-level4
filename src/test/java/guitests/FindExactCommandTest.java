package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.FindExactCommand;
import seedu.task.testutil.TestTask;

//@@author A0142487Y
public class FindExactCommandTest extends TaskManagerGuiTest {

    @Test
    public void findexact_nonEmptyList() {

        assertFindExactResult("fe buy pENcil", td.buy); // single result
        assertFindExactResult("findexact Give present", td.give); // single result
        assertFindExactResult("fe Mark"); // no results
        assertFindExactResult("finde apply", td.apply); // single result

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindExactResult("fexact apply"); // no result
    }

    @Test
    public void findexact_emptyList() {
        commandBox.runCommand("clear");
        assertFindExactResult("findexact Jean"); // no results
    }

    @Test
    public void findexact_invalidCommand_fail() {
        commandBox.runCommand("findexactgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void findexact_emptyKeywords_fail() {
        commandBox.runCommand("fe  ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindExactCommand.MESSAGE_USAGE));
    }

    private void assertFindExactResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
