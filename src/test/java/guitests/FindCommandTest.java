package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.testutil.TestTask;

//@@author A0162877N
public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_IsMutating() throws IllegalDateTimeValueException {
        FindCommand fc = new FindCommand("today");
        assertFalse(fc.isMutating());
    }

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find 38"); // no results
    }

    @Test
    public void find_nonEmptyListLabel() {
        assertFindResult("find owesMoney", td.task2); // find only 1 label
    }

    @Test
    public void find_nonEmptyListMultipleLabel() {
        assertFindResult("find owesMoney friends", td.task1, td.task2); //find 2 label
    }

    @Test
    public void find_nonEmptyListStartEndDate_singleResult() {
        assertFindResult("find by 10-11-2017 2359", td.task6); // 1 result
        assertFindResult("find from today to 11-11-2017 0000", td.task6); // 1 result
    }

    @Test
    public void find_nonEmptyListStartEndDate_pass() {

        assertFindResult("find from today to christmas",
                td.task1, td.task2, td.task3, td.task4, td.task5, td.task6, td.task7); // 7 result

        assertFindResult("find from 25-12-2017 2359 to 01-01-2017 2359",
                td.task1, td.task2, td.task3, td.task4, td.task5, td.task6, td.task7); // 7 result

        assertFindResult("find by christmas",
                td.task1, td.task2, td.task3, td.task4, td.task5, td.task6, td.task7); // 7 result

        assertFindResult("find by 25-12-2017",
                td.task1, td.task2, td.task3, td.task4, td.task5, td.task6, td.task7); // 7 result

        assertFindResult("find by 01-01-2016"); // 7 result

    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find task 1"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findsomething");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void find_validCommandNoInput_fail() {
        commandBox.runCommand("find"); // no input here
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void find_invalidDate_fail() {

        commandBox.runCommand("find by from to ");

        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
