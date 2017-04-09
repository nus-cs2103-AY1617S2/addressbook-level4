package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskit.commons.core.Messages;
import seedu.taskit.logic.commands.EditCommand;
import seedu.taskit.logic.commands.FindCommand;
import seedu.taskit.testutil.TestTask;

//@@author A0141872E
public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find assignment"); // no results
        assertFindResult("find HW", td.hw1, td.hw2);// multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find HW", td.hw2);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find HW"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findHW");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("find");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void find_byDate() {
        //find by time
        commandBox.runCommand("add date from 3pm to 6pm");
        assertFindResult("find 6pm", td.date);

        //find by month
        commandBox.runCommand("add internship by May 30");
        assertFindResult("find May", td.internship);
    }


    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
