package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.ListByCategoryCommand;
import seedu.taskboss.testutil.TestTask;


//---------------- Tests for FindCommand --------------------------------------

//@@author A0147990R
public class ListByCategoryCommandTest extends TaskBossGuiTest {

    @Test
    public void listByCategory_nonEmptyList() {
        // Equivalence partition: list tasks by a nonexistent category
        assertListByCategoryResult("list c/wife"); // no results

        // EP: list tasks by an existing category
        assertListByCategoryResult("list c/friends", td.taskA, td.taskB); // multiple results

    }

    // EP: apply list by category in an empty list
    @Test
    public void listByCategory_emptyList() {
        commandBox.runCommand("clear");
        assertListByCategoryResult("list c/work"); // no results
    }

    // EP: invalid command word
    @Test
    public void listByCategory_unknownCommand_fail() {
        commandBox.runCommand("listc");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    // EP: invalid command format
    @Test
    public void listByCategory_invalidCommand_fail() {
        commandBox.runCommand("list t/cate");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListByCategoryCommand.MESSAGE_USAGE));
    }

    private void assertListByCategoryResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
