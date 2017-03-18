package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.ListByCategoryCommand;
import seedu.taskboss.testutil.TestTask;

public class ListByCategoryCommandTest extends TaskBossGuiTest {
    @Test
    public void listByCategory_nonEmptyList() {
        assertListByCategoryResult("list c/wife"); // no results
        assertListByCategoryResult("list c/friends", td.alice, td.benson); // multiple results

    }

    @Test
    public void listByCategory_emptyList() {
        commandBox.runCommand("clear");
        assertListByCategoryResult("list c/friends"); // no results
    }

    @Test
    public void listByCategory_unknownCommand_fail() {
        commandBox.runCommand("listc");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

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
