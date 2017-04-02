package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.logic.commands.SortCommand;
import seedu.taskboss.testutil.TestTask;

//@@author A0143157J
public class SortCommandTest extends TaskBossGuiTest {

    //---------------- Tests for SortCommand --------------------------------------

    /*
     * Valid equivalence partitions:
     * - end date time
     * - start date time
     * - priority level
     */

    @Test
    public void sort() {
        // Equivalence partition: sort by end date time
        TestTask[] expectedList = new TestTask[] {td.taskC, td.taskE, td.taskA,
            td.taskD, td.taskB, td.taskG, td.taskF};
        assertSortSuccess(false, expectedList, "ed");

        // EP: sort by start date time
        TestTask[] expectedList2 = new TestTask[] {td.taskG, td.taskA, td.taskC,
            td.taskD, td.taskF, td.taskE, td.taskB};
        assertSortSuccess(false, expectedList2, "sd");

        // EP: sort by end date time short command
        TestTask[] expectedList3 = new TestTask[] {td.taskC, td.taskE, td.taskA,
            td.taskD, td.taskB, td.taskG, td.taskF};
        assertSortSuccess(true, expectedList3, "ed");

        // EP: sort by priority short command
        TestTask[] expectedList4 = new TestTask[] {td.taskC, td.taskA, td.taskD,
            td.taskG, td.taskE, td.taskB, td.taskF};
        assertSortSuccess(true, expectedList4, "p");

        // EP: invalid sort command
        commandBox.runCommand("sort byname");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // EP: invalid sort command
        commandBox.runCommand("sort"); // boundary value with no sort type specified
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // EP: invalid sort command
        commandBox.runCommand("s"); // boundary value with no sort type specified
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

    }

    private void assertSortSuccess(boolean isShortCommand, TestTask[] expectedList, String sortType) {
        if (isShortCommand) {
            commandBox.runCommand("s " + sortType);
        } else {
            commandBox.runCommand("sort " + sortType);
        }

        //confirm the list is sorted according to the specified sort type
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}

