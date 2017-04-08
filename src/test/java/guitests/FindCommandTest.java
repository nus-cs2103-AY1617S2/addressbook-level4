// @@author A0139399J
package guitests;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.doit.commons.core.Messages;
import seedu.doit.logic.commands.ClearCommand;
import seedu.doit.logic.commands.DeleteCommand;
import seedu.doit.logic.commands.FindCommand;
import seedu.doit.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    public static final String MESSAGE_FIND_COMMAND = FindCommand.COMMAND_WORD + " ";
    public static final String MESSAGE_TEST_CLEAR_COMMAND = ClearCommand.COMMAND_WORD;
    public static final String MESSAGE_TEST_DELETE_COMMAND = DeleteCommand.COMMAND_WORD + " 1";

    public static final String MESSAGE_TEST_FIND_MARK = "find n/Mark";
    public static final String MESSAGE_TEST_FIND_MEIER = "find n/Meier";
    public static final String MESSAGE_TEST_FIND_JEAN = "find n/Jean";
    public static final String MESSAGE_TEST_FIND_PRIORITY = "find p/high";
    public static final String MESSAGE_TEST_FIND_DESCRIPTION = "find d/l";
    public static final String MESSAGE_TEST_FIND_TAG = "find t/owesMoney";
    public static final String MESSAGE_TEST_FIND_STARTTIME = "find s/03/19/17";
    public static final String MESSAGE_TEST_FIND_ENDTIME = "find e/03/20/17";
    public static final String MESSAGE_TEST_FIND_INVALID = "findn/george";
    public static final String MESSAGE_TEST_INVALID_PARAMETERS = MESSAGE_FIND_COMMAND + "aaa";

    @Test
    public void find_name_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_MARK); // no results
        assertFindResult(MESSAGE_TEST_FIND_MEIER, this.td.benson, this.td.daniel); // multiple
                                                                                   // results

        // find after deleting one result
        this.commandBox.runCommand(MESSAGE_TEST_DELETE_COMMAND);
        assertFindResult(MESSAGE_TEST_FIND_MEIER, this.td.daniel);
    }

    @Test
    public void find_priority_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_PRIORITY, this.td.carl, this.td.daniel, this.td.george); // multiple
                                                                                                    // results
    }

    @Test
    public void find_description_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_DESCRIPTION, this.td.aF, this.td.bF, this.td.cF, this.td.aE, this.td.bE,
                this.td.cE); // multiple results
    }

    @Test
    public void find_tag_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_TAG, this.td.benson); // multiple
                                                                 // results
    }

    @Test
    public void find_startTime_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_STARTTIME, this.td.aE, this.td.bE, this.td.cE); // multiple
                                                                                           // results
    }

    @Test
    public void find_endTime_nonEmptyList() {
        assertFindResult(MESSAGE_TEST_FIND_ENDTIME, this.td.george, this.td.aE, this.td.bE, this.td.cE); // multiple
                                                                                                         // results
    }

    @Test
    public void find_emptyList() {
        this.commandBox.runCommand(MESSAGE_TEST_CLEAR_COMMAND);
        assertFindResult(MESSAGE_TEST_FIND_JEAN); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        this.commandBox.runCommand(MESSAGE_TEST_FIND_INVALID);
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        this.commandBox.runCommand(MESSAGE_FIND_COMMAND);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        this.commandBox.runCommand(MESSAGE_TEST_INVALID_PARAMETERS);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        this.commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertAllPanelsMatch(expectedHits);
    }
}
