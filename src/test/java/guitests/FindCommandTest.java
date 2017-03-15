package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_IsMutating() throws IllegalDateTimeValueException {
        FindCommand fc = new FindCommand("today");
        assertFalse(fc.isMutating());
    }

    @Test
    public void find_nonEmptyList() {
        assertFindResult("FIND 38"); // no results
        assertFindResult("FIND 3", td.task3); // multiple results

        //find after deleting one result
        commandBox.runCommand("DELETE 1");
        assertFindResult("FIND 3");
    }

    @Test
    public void find_nonEmptyListLabel() {
        assertFindResult("FIND owesMoney", td.task2); // find only 1 label
    }

    @Test
    public void find_nonEmptyListMultipleLabel() {
        assertFindResult("FIND owesMoney friends", td.task1, td.task2); //find 2 label
    }

    @Test
    public void find_nonEmptyListStartEndDate_singleResult() {
        assertFindResult("FIND BY 10-11-2017 2359", td.task6); // 1 result
        assertFindResult("FIND FROM today TO 11-11-2017 0000", td.task6); // 1 result
    }

    @Test
    public void find_nonEmptyListStartEndDate_pass() {
        assertFindResult("FIND FROM today TO christmas",
                td.task1, td.task2, td.task3, td.task4, td.task5, td.task6, td.task7); // 7 result
        assertFindResult("FIND FROM 25-12-2017 2359 TO 01-01-2017 2359",
                td.task1, td.task2, td.task3, td.task4, td.task5, td.task6, td.task7); // 7 result
        assertFindResult("FIND BY christmas",
                td.task1, td.task2, td.task3, td.task4, td.task5, td.task6, td.task7); // 7 result
        assertFindResult("FIND BY 25-12-2017",
                td.task1, td.task2, td.task3, td.task4, td.task5, td.task6, td.task7); // 7 result
        assertFindResult("FIND BY 01-01-2016"); // 7 result
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("CLEAR");
        assertFindResult("FIND task 1"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findsomething");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void find_validCommandNoInput_fail() {
        commandBox.runCommand("FIND"); // no input here
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void find_invalidDate_fail() {
        commandBox.runCommand("FIND BY FROM TO ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
