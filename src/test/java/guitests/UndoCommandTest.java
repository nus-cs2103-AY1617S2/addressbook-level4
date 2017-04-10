package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.DeleteCommand;
import seedu.tache.logic.commands.EditCommand;
import seedu.tache.logic.commands.UndoCommand;
import seedu.tache.testutil.TestTask;

//@@author A0150120H
public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo_addCommand_success() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.getFit;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertUndoSuccess(currentList);

        //TODO: Add undo cases for other commands
    }

    @Test
    public void undo_deleteCommand_success() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_editCommand_success() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand(EditCommand.COMMAND_WORD + " 5 change ed to 5th November");
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_emptyHistory_failure() {
        //nothing to undo
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(UndoCommand.MESSAGE_EMPTY_HISTORY);
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
