package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.today.commons.core.Messages;
import seedu.today.commons.exceptions.IllegalValueException;
import seedu.today.logic.commands.RedoCommand;
import seedu.today.logic.commands.UndoCommand;

// @@author A0144315N
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
        assertShowErrorMessage(Messages.MESSAGE_INVALID_COMMAND_FORMAT);

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
