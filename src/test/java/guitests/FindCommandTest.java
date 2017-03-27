package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.FindCommand;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.testutil.TestTask;

//@@author A0147990R
public class FindCommandTest extends TaskBossGuiTest {

    //---------------- Tests for FindCommand --------------------------------------

    /*
     * Valid equivalence partitions:
     * - task keywords (name and information)
     * - start datetime
     * - end datetime
     */

    // Equivalence partition: find keywords in a non-empty list
    @Test
    public void find_byKeywordNonEmptyList() {
        assertFindResult("find k/Hey"); // no results
        assertFindResult("find k/code", td.taskE, td.taskD); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 2");
        assertFindResult("find k/code", td.taskE);
    }

    // EP: find name in an empty list
    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find k/meeting"); // no results
    }

    // EP: find start datetime with short command
    @Test
    public void find_usingShortCommand() {
        assertFindResult("f sd/Jul"); // no results
        assertFindResult("f sd/5pm", td.taskE, td.taskA, td.taskG); // multiple results
    }

    //@@author A0143157J
    // EP: find end datetime by natural language
    @Test
    public void find_usingNaturalLanguage_endDate() {
        assertFindResult("find ed/28 february", td.taskC, td.taskE);
    }

    // EP: find month by natural language
    @Test
    public void find_usingNaturalLanguage_onlyMonth() {
        assertFindResult("find sd/january", td.taskG);
    }

    // EP: find day_of_month by natural language
    @Test
    public void find_usingNaturalLanguage_onlyDayOfMonth() {
        assertFindResult("find ed/28", td.taskC, td.taskE, td.taskA, td.taskD, td.taskB, td.taskG);
    }

    //EP: invalid date
    @Test
    public void find_invalidDate_fail() {
        commandBox.runCommand("find sd/mynameisbob");
        assertResultMessage(DateTime.MESSAGE_DATE_CONSTRAINTS);
    }

    //EP: invalid prefix
    @Test
    public void find_invalidPrefix_fail() {
        commandBox.runCommand("find keyword/");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    //@@author A0147990R
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
