package guitests;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {

        commandBox.runCommand("clear");
        commandBox.runCommand(td.police.getAddCommand());
        commandBox.runCommand(td.jog.getAddCommand());

        assertFindResult("find whatever"); // no results
        assertFindResult("find address", td.police); // multiple results
        assertFindResult("find change", td.jog, td.police); // multiple results

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find jog", td.jog);
        assertFindResult("find address");

    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
    }
}
