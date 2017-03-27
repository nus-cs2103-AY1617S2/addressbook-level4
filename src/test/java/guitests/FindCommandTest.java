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
    private final String SINGLE_TAG = "cat";

    // This tag tagged multiple items (td.dog, td.cat) in the list of test todos
    private final String MULTI_TAG = "petcare";

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

    private void assertFindResult(String command, TestTodo... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " todos listed!");
        assertTrue(todoListPanel.isListMatching(true, expectedHits));
    }
}
