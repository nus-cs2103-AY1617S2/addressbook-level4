package guitests;

//import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.logic.commands.FindCommand;
import seedu.onetwodo.testutil.TestTask;

public class FindCommandTest extends ToDoListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult(FindCommand.COMMAND_WORD + " Nemo"); // no results
        assertFindResult(FindCommand.COMMAND_WORD + " change", td.taskH, td.taskI); // multiple results

        //find after deleting one result
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " t2");
        assertFindResult(FindCommand.COMMAND_WORD + " change", td.taskH);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertFindResult(FindCommand.COMMAND_WORD + " Dory"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("finds something");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
       // assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        //assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
