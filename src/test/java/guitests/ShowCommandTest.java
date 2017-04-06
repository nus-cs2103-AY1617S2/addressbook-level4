//@@author A0105748B
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.bulletjournal.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.bulletjournal.commons.core.Messages;
import seedu.bulletjournal.logic.commands.ShowCommand;
import seedu.bulletjournal.testutil.TestTask;

public class ShowCommandTest extends TodoListGuiTest {

    @Test
    public void showNonEmptyList() {
        assertShowResult("show done", td.buymilk, td.creatework, td.eatleftovers); // multiple results
        assertShowResult("show undone", td.assignment, td.dumpmilk, td.findsocks, td.getclothes); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertShowResult("show done", td.buymilk, td.creatework, td.eatleftovers);
        assertShowResult("show undone", td.dumpmilk, td.findsocks, td.getclothes);
    }

    @Test
    public void showEmptyList() {
        commandBox.runCommand("clear");
        assertShowResult("show done"); // no results
    }

    @Test
    public void showInvalidCommandFail() {
        commandBox.runCommand("showdone");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("show finished"); // invalid command
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    private void assertShowResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
