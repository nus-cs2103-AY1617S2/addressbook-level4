package guitests;

import org.junit.Test;

import seedu.doit.commons.core.Messages;
import seedu.doit.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_name_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        assertFindResult("find Meier", td.benson, td.daniel); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier", td.daniel);
    }

    //@@author A0139399J
    @Test
    public void find_priority_nonEmptyList() {
        assertFindResult("find p/high", td.carl, td.daniel, td.george); // multiple results
    }

    //@@author A0139399J
    @Test
    public void find_description_nonEmptyList() {
        assertFindResult("find d/l", td.aF, td.bF, td.cF, td.aE, td.bE, td.cE); // multiple results
    }

    //@@author A0139399J
    @Test
    public void find_tag_nonEmptyList() {
        assertFindResult("find t/owesMoney", td.benson); // multiple results
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
        assertAllPanelsMatch(expectedHits);
    }
}
