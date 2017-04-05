// @@author A0139399J
package guitests;

import org.junit.Test;

import seedu.doit.commons.core.Messages;
import seedu.doit.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    public static final String MESSAGE_FIND_COMMAND = "find ";
    public static final String MESSAGE_TEST_CLEAR_COMMAND = "clear";
    public static final String MESSAGE_TEST_DELETE_COMMAND = "delete 1";

    public static final String MESSAGE_TEST_FIND_MARK = "find n/Mark";
    public static final String MESSAGE_TEST_FIND_MEIER = "find n/Meier";
    public static final String MESSAGE_TEST_FIND_JEAN = "find n/Jean";
    public static final String MESSAGE_TEST_FIND_PRIORITY = "find p/high";
    public static final String MESSAGE_TEST_FIND_DESCRIPTION = "find d/l";
    public static final String MESSAGE_TEST_FIND_TAG = "find t/owesMoney";
    public static final String MESSAGE_TEST_FIND_STARTTIME = "find s/03/19/17";
    public static final String MESSAGE_TEST_FIND_ENDTIME = "find e/03/20/17";
    public static final String MESSAGE_TEST_FIND_INVALID = "findn/george";

    @Test
    public void find_name_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_MARK); // no results
        assertFindResult(MESSAGE_TEST_FIND_MEIER, td.benson, td.daniel); // multiple results

        //find after deleting one result
        commandBox.runCommand(MESSAGE_TEST_DELETE_COMMAND);
        assertFindResult(MESSAGE_TEST_FIND_MEIER, td.daniel);
    }

    @Test
    public void find_priority_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_PRIORITY, td.carl, td.daniel, td.george); // multiple results
    }

    @Test
    public void find_description_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_DESCRIPTION, td.aF, td.bF, td.cF, td.aE, td.bE, td.cE); // multiple results
    }

    @Test
    public void find_tag_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_TAG, td.benson); // multiple results
    }

    @Test
    public void find_startTime_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_STARTTIME, td.aE, td.bE, td.cE); // multiple results
    }

    @Test
    public void find_endTime_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_ENDTIME, td.george, td.aE, td.bE, td.cE); // multiple results
    }



    @Test
    public void find_emptyList() {
        commandBox.runCommand(MESSAGE_TEST_CLEAR_COMMAND);
        assertFindResult(MESSAGE_TEST_FIND_JEAN); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand(MESSAGE_TEST_FIND_INVALID);
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertAllPanelsMatch(expectedHits);
    }
}
