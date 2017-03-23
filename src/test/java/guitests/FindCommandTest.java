package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.testutil.TestTask;

public class FindCommandTest extends TaskBossGuiTest {

    //---------------- Tests for FindCommand --------------------------------------

    /*
     * Valid equivalence partitions:
     * - task keywords (name and information)
     * - start datetime
     * - end datetime
     * 
     */

    // Equivalence partition: find by name in a non-empty list
    @Test
    public void find_byKeywordNonEmptyList() {
        assertFindResult("find k/Mark"); // no results
        assertFindResult("find k/Meier", td.daniel, td.benson); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 2");
        assertFindResult("find k/Meier", td.daniel);
    }
    // EP: find by name in an empty list
    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find k/Jean"); // no results
    }

    // EP: find by start datetime with short command
    @Test
    public void find_usingShortCommand() {
        assertFindResult("f sd/Jul"); // no results
        assertFindResult("f sd/5:00 PM", td.elle, td.alice, td.george); // multiple results
    }

    //EP: invalid command word
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
