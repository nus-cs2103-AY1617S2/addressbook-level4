package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        //assertFindResult("find Mark"); // no results
        assertFindResult("find Meier", td.benson, td.daniel, td.elle); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier", td.daniel, td.elle);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("reset");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void find_byDate_success() {
        assertFindResult("find for: today", td.alice, td.benson, td.carl, td.daniel, td.elle, td.fiona, td.george);
        assertFindResult("find for: tomorrow");
    }

    @Test
    public void find_byTags_success() {
        assertFindResult("find #friends", td.alice, td.benson);
        assertFindResult("find #starWars");
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
