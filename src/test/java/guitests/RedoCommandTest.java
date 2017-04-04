package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import typetask.logic.commands.RedoCommand;
import typetask.testutil.TestTask;

//@@author A0139926R
public class RedoCommandTest extends AddressBookGuiTest {

    private final String CLEAR_COMMAND = "clear";
    private final String UNDO_COMMAND = "undo";
    private final String REDO_COMMAND = "redo";
    private final String REDO_SHORT_COMMAND = "r";
    @Test
    public void redo_clear_success() {
        TestTask[] expectedTaskList = {};

        commandBox.runCommand(CLEAR_COMMAND);
        commandBox.runCommand(UNDO_COMMAND);
        assertRedoSuccess(expectedTaskList);

        commandBox.runCommand(UNDO_COMMAND);

        commandBox.runCommand(CLEAR_COMMAND);
        commandBox.runCommand(UNDO_COMMAND);
        assertRedoShortCommandSuccess(expectedTaskList);
    }
    @Test
    public void redo_fail() {
        commandBox.runCommand(REDO_COMMAND);
        assertResultMessage(RedoCommand.MESSAGE_FAILURE);

        commandBox.runCommand(REDO_SHORT_COMMAND);
        assertResultMessage(RedoCommand.MESSAGE_FAILURE);
    }
    private void assertRedoSuccess(TestTask[] expectedList) {
        commandBox.runCommand(REDO_COMMAND);
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        assertTrue(personListPanel.isListMatching(expectedList));
    }
    private void assertRedoShortCommandSuccess(TestTask[] expectedList) {
        commandBox.runCommand(REDO_SHORT_COMMAND);
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        assertTrue(personListPanel.isListMatching(expectedList));
    }
}
