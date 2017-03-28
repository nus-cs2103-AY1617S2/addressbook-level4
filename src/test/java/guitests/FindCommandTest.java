package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.DeleteCommand;
import seedu.todolist.logic.commands.FindCommand;
import seedu.todolist.logic.parser.CliSyntax;
import seedu.todolist.testutil.TestTodo;

public class FindCommandTest extends TodoListGuiTest {
    // This tag only tagged one item (td.cat) in the list of test todos
    private static final String SINGLE_TAG = "cat";

    // This tag tagged multiple items (td.dog, td.cat) in the list of test todos
    private static final String MULTI_TAG = "petcare";

    private static final String START_TIME_AFTER = "1:00PM 11/11/17";
    private static final String START_TIME_BEFORE = "11:00AM 11/11/17";
    private static final String END_TIME_AFTER = "7:00PM 11/11/17";
    private static final String END_TIME_BEFORE = "5:00PM 11/11/17";
    private static final String COMPLETE_TIME_AFTER = "7:00PM 11/11/17";
    private static final String COMPLETE_TIME_BEFORE = "5:00PM 11/11/17";

    @Test
    public void find_nonEmptyList() {
        assertFindResult(FindCommand.COMMAND_WORD + " supper"); // no results
        assertFindResult(FindCommand.COMMAND_WORD + " homework", td.math, td.english); // multiple results

        //find after deleting one result

        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFindResult(FindCommand.COMMAND_WORD + " homework", td.english);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult(FindCommand.COMMAND_WORD + " homework"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand(FindCommand.COMMAND_WORD + "homework");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    //@@author A0163720M
    /**
     * Test to find todo with just one tag e.g. tags = some_Tag
     */
    public void find_todo_with_one_tag() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_TAG.getPrefix() + SINGLE_TAG, td.cat);
    }
    //@@author

    @Test
    //@@author A0163720M
    /**
     * Test to find todo with multiple tags e.g. tags = some_tag, some_tag2
     */
    public void find_todo_with_multiple_tags() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_TAG.getPrefix() + MULTI_TAG, td.dog, td.cat);
    }
    //@@author

    @Test
    public void find_startTimeAfter() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_START_TIME.getPrefix() + START_TIME_AFTER,
            td.toilet, td.tennis, td.lunch);
    }

    @Test
    public void find_startTimeBefore() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_START_TIME.getPrefix() + START_TIME_BEFORE,
            td.lunch);
    }

    @Test
    public void find_endTimeAfter() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_END_TIME.getPrefix() + END_TIME_AFTER,
            td.essay, td.library, td.tennis, td.job, td.lunch);
    }

    @Test
    public void find_endTimeBefore() {
        assertFindResult(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_END_TIME.getPrefix() + END_TIME_BEFORE,
            td.tennis, td.lunch);
    }

    @Test
    public void find_completeTimeAfter() {
        assertFindResult(FindCommand.COMMAND_WORD + " "
            + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + COMPLETE_TIME_AFTER,
            td.car, td.library, td.tennis);
    }

    @Test
    public void find_completeTimeBefore() {
        assertFindResult(FindCommand.COMMAND_WORD + " "
            + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + COMPLETE_TIME_BEFORE,
            td.tennis);
    }

    private void assertFindResult(String command, TestTodo... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " todos listed!");
        assertTrue(todoListPanel.isListMatching(true, expectedHits));
    }
}
