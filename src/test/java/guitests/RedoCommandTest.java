package guitests;

import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Test;

import seedu.todolist.MainApp;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.RedoCommand;
import seedu.todolist.logic.commands.UndoCommand;
import seedu.todolist.testutil.TestTodo;
import seedu.todolist.testutil.TestUtil;

public class RedoCommandTest extends TodoListGuiTest {
  //@@author A0163786N
    /**
     * The list of todos in the todo list panel is expected to match this list
     */
    private TestTodo[] originalList = td.getTypicalTodos();
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    @Test
    public void redo_noActionToRedo_failure() {
        commandBox.runCommand(RedoCommand.COMMAND_WORD);
        assertResultMessage(RedoCommand.MESSAGE_NO_ACTION);
    }

    @Test
    public void redo_undoFailure_failure() {
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);
        assertResultMessage(RedoCommand.MESSAGE_NO_ACTION);
    }

    @Test
    public void redo_undoSuccess_success() {
        TestTodo[] currentList = TestUtil.addTodosToList(originalList, td.laundry);
        commandBox.runCommand(td.laundry.getAddCommand());
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertRedoSuccess(currentList);
    }

    @Test
    public void redo_modifyStateHistory_failure() {
        commandBox.runCommand(td.laundry.getAddCommand());
        logger.info("Added new todo");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        logger.info("Undid add");
        commandBox.runCommand(td.laundry.getAddCommand());
        logger.info("Added todo again");
        commandBox.runCommand(RedoCommand.COMMAND_WORD);
        logger.info("Ran redo command");
        assertResultMessage(RedoCommand.MESSAGE_NO_ACTION);
    }

    @Test
    public void redo_invalidCommand_failure() {
        commandBox.runCommand("redoes");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Runs redo command and asserts resulting list matches currentList
     */
    private void assertRedoSuccess(final TestTodo[] currentList) {
        commandBox.runCommand(RedoCommand.COMMAND_WORD);
        // confirm the list now contains all previous todos
        assertTrue(todoListPanel.isListMatching(true, currentList));
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }

}
