package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.UndoCommand;

//@@author A0139926R
public class UndoCommandTest extends AddressBookGuiTest {

    private final String ADD_COMMAND = "add test";
    private final String EDIT_COMMAND = "edit 1 by: tmr";
    private final String DELETE_COMMAND = "delete 1";
    private final String CLEAR_COMMAND = "clear";
    private final String LIST_COMMAND = "list";
    private final String FIND_COMMAND = "find alice";
    private final String UNDO_COMMAND = "undo";
    private final String UNDO_SHORT_COMMAND = "u";
    @Test
    public void undo_add_success() throws IllegalValueException {
        commandBox.runCommand(ADD_COMMAND);
        assertUndoSuccess();

        commandBox.runCommand(ADD_COMMAND);
        assertUndoShortcutSuccess();
    }
    @Test
    public void undo_edit_success() throws IllegalValueException {
        commandBox.runCommand(EDIT_COMMAND);
        assertUndoSuccess();

        commandBox.runCommand(EDIT_COMMAND);
        assertUndoShortcutSuccess();
    }
    @Test
    public void undo_delete_success() {
        commandBox.runCommand(DELETE_COMMAND);
        assertUndoSuccess();

        commandBox.runCommand(DELETE_COMMAND);
        assertUndoShortcutSuccess();
    }
    @Test
    public void undo_clear_success() {
        commandBox.runCommand(CLEAR_COMMAND);
        assertUndoSuccess();

        commandBox.runCommand(CLEAR_COMMAND);
        assertUndoShortcutSuccess();
    }
    @Test
    public void undo_list_fail() {
        commandBox.runCommand(LIST_COMMAND);
        commandBox.runCommand(UNDO_COMMAND);
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);

        commandBox.runCommand(LIST_COMMAND);
        commandBox.runCommand(UNDO_SHORT_COMMAND);
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);
    }
    @Test
    public void undo_find_fail() {
        commandBox.runCommand(FIND_COMMAND);
        commandBox.runCommand(UNDO_COMMAND);
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);

        commandBox.runCommand(FIND_COMMAND);
        commandBox.runCommand(UNDO_SHORT_COMMAND);
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);
    }
    @Test
    public void undo_nothingToUndo_fail() {
        commandBox.runCommand(UNDO_COMMAND);
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);

        commandBox.runCommand(UNDO_SHORT_COMMAND);
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);
    }
    private void assertUndoSuccess() {
        commandBox.runCommand(UNDO_COMMAND);
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        assertTrue(personListPanel.isListMatching(td.getTypicalTasks()));
    }
    private void assertUndoShortcutSuccess() {
        commandBox.runCommand(UNDO_SHORT_COMMAND);
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        assertTrue(personListPanel.isListMatching(td.getTypicalTasks()));
    }
}
