package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.FindCommand;
import seedu.tache.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList_success() {
        assertFindResult("find Dinner"); // no results
        assertFindResult("find Visit", td.visitSarah, td.visitFriend); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Visit", td.visitFriend);
    }

    @Test
    public void find_emptyList_failure() {
        commandBox.runCommand("clear");
        assertFindResult("find Book"); // no results
    }

    @Test
    public void find_invalidCommand_failure() {
        commandBox.runCommand("findsoftwareengineering");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    //@@author A0142255M
    @Test
    public void find_noFilter_failure() {
        commandBox.runCommand(FindCommand.COMMAND_WORD + " ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void find_shortCommand_success() {
        assertFindResult(FindCommand.SHORT_COMMAND_WORD + " eggs", td.eggsAndBread);
    }

    @Test
    public void find_dateTime_success() {
        // start date and end date
        assertFindResult(FindCommand.COMMAND_WORD + " 01/04/2017", td.eggsAndBread);

        // start time and end time
        assertFindResult(FindCommand.COMMAND_WORD + " 23:59:59", td.readBook);
    }

    @Test
    public void find_tag_success() {
        assertFindResult(FindCommand.COMMAND_WORD + " HighPriority", td.eggsAndBread);
    }
    //@@author

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " task(s) listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
