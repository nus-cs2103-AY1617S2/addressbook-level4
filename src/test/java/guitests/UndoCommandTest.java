package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tache.logic.commands.UndoCommand;
import seedu.tache.testutil.TestTask;

//@@author A0150120H
public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo() {
        //nothing to undo
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(UndoCommand.MESSAGE_EMPTY_HISTORY);

        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.getFit;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertUndoSuccess(currentList);

        //TODO: Add undo cases for other commands
    }

    private void assertUndoSuccess(TestTask... originalList) {
        commandBox.runCommand(UndoCommand.COMMAND_WORD);

        //confirm the list now contains the previous tasks
        assertTrue(taskListPanel.isListMatching(originalList));
    }
}
