package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.testutil.TestTask;

public class FindCommandTest extends TaskBossGuiTest {

    @Test
    public void find_byNameNonEmptyList() {
        assertFindResult("find n/Mark"); // no results
        assertFindResult("find n/Meier", td.benson, td.daniel); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find n/Meier", td.daniel);
    }

    @Test
    public void find_byInformationNonEmptyList() {
        assertFindResult("find i/Information"); // no results
        assertFindResult("find i/ave", td.alice, td.benson, td.elle); // multiple results
    }

    @Test
    public void find_usingShortCommand() {
        assertFindResult("f sd/Dec"); // no results
        assertFindResult("f sd/Feb 18", td.alice, td.carl, td.daniel); // multiple results
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find n/Jean"); // no results
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
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
