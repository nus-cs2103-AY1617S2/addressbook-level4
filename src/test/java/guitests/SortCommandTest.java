package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.logic.commands.SortCommand;
import seedu.taskboss.testutil.TestTask;

public class SortCommandTest extends TaskBossGuiTest {

    @Test
    public void sort() {
        //sort by end date time
        TestTask[] expectedList = new TestTask[] {td.carl, td.elle, td.alice,
            td.daniel, td.benson, td.george, td.fiona};
        assertSortSuccess(false, expectedList, "ed");

        //sort by start date time
        TestTask[] expectedList2 = new TestTask[] {td.george, td.alice, td.carl,
            td.daniel, td.fiona, td.elle, td.benson};
        assertSortSuccess(false, expectedList2, "sd");

        //sort by end date time short command
        TestTask[] expectedList3 = new TestTask[] {td.carl, td.elle, td.alice,
            td.daniel, td.benson, td.george, td.fiona};
        assertSortSuccess(true, expectedList3, "ed");

        //invalid sort command
        commandBox.runCommand("sort asdsa");
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

