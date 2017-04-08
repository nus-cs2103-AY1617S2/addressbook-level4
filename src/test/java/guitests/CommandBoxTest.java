package guitests;

import static org.junit.Assert.assertEquals;
import static t09b1.today.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import t09b1.today.commons.core.Messages;
import t09b1.today.commons.exceptions.IllegalValueException;
import t09b1.today.logic.commands.AddCommand;
import t09b1.today.logic.commands.RedoCommand;
import t09b1.today.logic.commands.UndoCommand;

// @@Author A0144315N
public class CommandBoxTest extends TaskManagerGuiTest {

    @Test
    public void add() throws IllegalArgumentException, IllegalValueException {

        // Enter empty string
        commandBox.runCommand("");
        assertNoErrorMessage();

        // Enter unknown command
        commandBox.runCommand("aaaaaaa");
        assertShowErrorMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        // Enter invalid command
        commandBox.runCommand("add !@#$%^&");
        assertShowErrorMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // Test no command left to undo/redo message
        commandBox.runCommand("undo");
        assertShowErrorMessage(UndoCommand.MESSAGE_NO_PREV_COMMAND);
        commandBox.runCommand("redo");
        assertShowErrorMessage(RedoCommand.MESSAGE_NO_PREV_COMMAND);
    }

    // ----- Helper -----
    private void assertNoErrorMessage() {
        String errorMessage = commandBox.getCommandBoxInstance().getValidators().get(0).getMessage();
        assertEquals(errorMessage, "");
    }

    private void assertShowErrorMessage(String expectedMessage) {
        String errorMessage = commandBox.getCommandBoxInstance().getValidators().get(0).getMessage();
        assertEquals(errorMessage, expectedMessage);
    }
}
