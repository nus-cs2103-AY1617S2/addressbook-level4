package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.UndoCommand;
import seedu.todolist.testutil.TestTodo;

public class UndoCommandTest extends TodoListGuiTest {
    //@@author A0163786N
    /**
     * The list of todos in the todo list panel is expected to match this list
     */
    private TestTodo[] originalList = td.getTypicalTodos();

    @Test
    public void undo_noActionToUndo_failure() {
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(UndoCommand.MESSAGE_NO_ACTION);
    }

    @Test
    public void undo_listTodos_failure() {
        commandBox.runCommand("list");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(UndoCommand.MESSAGE_NO_ACTION);
    }

    @Test
    public void undo_addValidFloatingTask_success() {
        commandBox.runCommand(td.laundry.getAddCommand());
        assertUndoSuccess();
    }

    @Test
    public void undo_addValidDeadline_success() {
        commandBox.runCommand(td.job.getAddCommand());
        assertUndoSuccess();
    }

    @Test
    public void undo_addValidEvent_success() {
        commandBox.runCommand(td.lunch.getAddCommand());
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

    @Test
    public void undo_completeValidTodo_success() {
        commandBox.runCommand("complete 1");
        assertUndoSuccess();
    }

    @Test
    public void undo_uncompleteValidTodo_success() {
        commandBox.runCommand("uncomplete 10");
        assertUndoSuccess();
    }

    @Test
    public void undo_invalidCommand_failure() {
        commandBox.runCommand("undoes");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Runs undo command and asserts resulting list matches currentList
     */
    private void assertUndoSuccess() {
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        // confirm the list now contains all previous todos
        assertTrue(todoListPanel.isListMatching(true, originalList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
}
