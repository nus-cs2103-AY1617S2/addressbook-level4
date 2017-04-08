package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.UndoCommand;
import seedu.tache.testutil.TestTask;

//@@author A0150120H
public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo() {
        //nothing to undo
        commandBox.runCommand("clear");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(UndoCommand.MESSAGE_EMPTY_HISTORY);

        //add one task
        TestTask[] currentList = new TestTask[0];
        TestTask taskToAdd = td.getFit;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertUndoSuccess(currentList);

        //TODO: Add undo cases for other commands
    }

    //@@author A0142255M
    @Test
    public void undo_shortCommand_success() {
        commandBox.runCommand("edit 1; name Pay David 15 for cab");
        commandBox.runCommand(UndoCommand.SHORT_COMMAND_WORD + " ");
        assertUndoSuccess(td.getTypicalTasks());
    }

    @Test
    public void undo_additionalParameters_success() {
        commandBox.runCommand("delete 2");
        commandBox.runCommand(UndoCommand.SHORT_COMMAND_WORD + " delete");
        assertUndoSuccess(td.getTypicalTasks());
    }

    @Test
    public void undo_invalidCommand_failure() {
        commandBox.runCommand(td.findGirlfriend.getAddCommand());
        commandBox.runCommand("undoodifbshvh");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        assertFalse(taskListPanel.getNumberOfTasks() == td.getTypicalTasks().length);
    }
    //@@author

    private void assertUndoSuccess(TestTask... originalList) {
        commandBox.runCommand(UndoCommand.COMMAND_WORD);

        //confirm the list now contains the previous tasks
        assertTrue(taskListPanel.isListMatching(originalList));
    }
}
