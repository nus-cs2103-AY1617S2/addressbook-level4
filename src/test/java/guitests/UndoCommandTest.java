package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UndoCommand;

public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo_nothingToUndo_fail() {
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_PREV_COMMAND);
    }

    @Test
    public void undo_add_success() throws IllegalArgumentException, IllegalValueException {
        // Undo an add task
        String commandText = "add test task";
        commandBox.runCommand(commandText);
        assertUndoSuccess(commandText);
    }

    private void assertUndoSuccess(String commandText) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("undo");
        assertResultMessage(String.format(UndoCommand.MESSAGE_SUCCESS, commandText));
        assertTrue(futureTaskListPanel.isListMatching(td.getTypicalTasks()));
    }

}
