package guitests;

import org.junit.Test;

import seedu.address.logic.commands.UndoCommand;
import seedu.address.testutil.TestTask;

public class UndoCommandTest extends AddressBookGuiTest {

    @Test
    public void undo_emptySession_failure() {
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_BACKWARDS_COMMAND);
    }

    @Test
    public void undo_noPreviousCommand_failure() {
        //End of state stack
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_BACKWARDS_COMMAND);

        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_BACKWARDS_COMMAND);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_BACKWARDS_COMMAND);
    }

    @Test
    public void undo_addThenUndo_success() throws Exception {
        TestTask taskToAdd = td.hoon;
        commandBox.runCommand(taskToAdd.getAddCommand());

        assertUndoSuccess();
    }

    @Test
    public void undo_clearThenUndo_success() throws Exception {
        commandBox.runCommand("clear");

        assertUndoSuccess();
    }

    @Test
    public void undo_deleteThenUndo_success() throws Exception {
        commandBox.runCommand("delete 1");

        assertUndoSuccess();
    }

    @Test
    public void undo_editThenUndo_success() throws Exception {
        String detailsToEdit = "Bobby d/91234567 p/1 i/Block 123, Bobby Street 3 t/husband";
        int taskListIndex = 1;

        commandBox.runCommand("edit " + taskListIndex + " " + detailsToEdit);

        assertUndoSuccess();
    }

    /**
     * Runs the undo command to undo the previously executed command and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first person in the list,
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertUndoSuccess() {
        commandBox.runCommand("undo");

        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);

    }

}

