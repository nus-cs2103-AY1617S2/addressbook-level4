package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.bulletjournal.commons.core.Messages;
import seedu.bulletjournal.testutil.TestTask;

public class ShowCommandTest extends AddressBookGuiTest {

    @Test
    public void show_nonEmptyList() {
        assertShowResult("show finished"); // no results
        assertShowResult("show done", td.buymilk, td.creatework, td.eatleftovers); // multiple results
        assertShowResult("show undone", td.assignment, td.dumpmilk, td.findsocks, td.getclothes); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertShowResult("show done", td.buymilk, td.creatework, td.eatleftovers);
        assertShowResult("show undone", td.dumpmilk, td.findsocks, td.getclothes);
    }

    @Test
    public void show_emptyList() {
        commandBox.runCommand("clear");
        assertShowResult("show done"); // no results
    }

    @Test
    public void show_invalidCommand_fail() {
        commandBox.runCommand("showdone");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertShowResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " persons listed!");
        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
