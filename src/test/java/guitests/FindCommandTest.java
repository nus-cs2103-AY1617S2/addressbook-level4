package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.logic.commands.FindCommand;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestTask;

public class FindCommandTest extends ToDoListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertNoFindResults(FindCommand.COMMAND_WORD + " Nemo");
        assertFindResult(FindCommand.COMMAND_WORD + " change", td.taskH.getTaskType(), td.taskH, td.taskI);
    }

    @Test
    public void find_nonEmptyList_afterDeleted() {
        //find after deleting one result taskH
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " t2");
        assertFindResult(FindCommand.COMMAND_WORD + " change", td.taskH.getTaskType(), td.taskI);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertNoFindResults(FindCommand.COMMAND_WORD + " Dory");
    }


    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("finds something");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertNoFindResults(String command) {
        assertFindResult(command, TaskType.DEADLINE);
    }

    private void assertFindResult(String command, TaskType type, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(type, expectedHits));
    }
}
