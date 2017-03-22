package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.testutil.TestTask;

public class FindCommandTest extends TaskBossGuiTest {

    //---------------- Tests for FindCommand --------------------------------------

    // Equivalence partition: find by name in a non-empty list
    @Test
    public void find_byNameNonEmptyList() {
        assertFindResult("find n/Mark"); // no results
        assertFindResult("find n/Meier", td.daniel, td.benson); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 2");
        assertFindResult("find n/Meier", td.daniel);
    }
    // EP: find by name in an empty list
    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find n/Jean"); // no results
    }

    // EP: find by information in a non-empty list
    @Test
    public void find_byInformationNonEmptyList() {
        assertFindResult("find i/Information"); // no results
        assertFindResult("find i/ave", td.elle, td.alice, td.benson); // multiple results
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
