package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.UndoCommand;
import seedu.address.testutil.TestTodo;

public class UndoCommandTest extends TodoListGuiTest {

    /**
     * The list of todos in the todo list panel is expected to match this list
     */
    TestTodo[] originalList = td.getTypicalTodos();

    @Test
    public void undo_noActionToUndo_failure() {
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(UndoCommand.MESSAGE_NO_ACTION);
    }

    @Test
    public void undo_addValidTodo_success() {
        commandBox.runCommand(td.laundry.getAddCommand());
        assertUndoSuccess();
    }

    @Test
    public void undo_deleteValidTodo_success() {
        commandBox.runCommand("delete 1");
        assertUndoSuccess();
    }

    @Test
    public void undo_clearTodos_success() {
        commandBox.runCommand("clear");
        assertUndoSuccess();
    }

    @Test
    public void undo_editValidTodo_success() {
        commandBox.runCommand("edit 1 Feed the dog");
        assertUndoSuccess();
    }

    /**
     * Runs undo command and asserts resulting list matches currentList
     * @param currentList
     */
    private void assertUndoSuccess() {

        commandBox.runCommand(UndoCommand.COMMAND_WORD);

        // confirm the list now contains all previous todos
        assertTrue(todoListPanel.isListMatching(originalList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
}
