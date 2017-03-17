package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TODO_SUCCESS;

import org.junit.Test;

import guitests.guihandles.TodoCardHandle;
import seedu.address.testutil.TestTodo;
import seedu.address.testutil.TestUtil;

public class UdoCommandTest extends TodoListGuiTest {
    @Test
    public void undoAdd() {
        //add one todo
        TestTodo[] currentList = td.getTypicalTodos();
        TestTodo floatingTodoToAdd = td.hoon;

        //undo floating task
        assertAddSuccess(floatingTodoToAdd, currentList);
        assertUndoSuccess(currentList);

        //undo event
        TestTodo todoToAdd = td.eventTest;
        assertAddSuccess(todoToAdd, currentList);
        assertUndoSuccess(currentList);
    }

    @Test
    public void undoClear() {
        TestTodo[] currentList = td.getTypicalTodos();
        //undo clear
        assertClearCommandSuccess();
        assertUndoSuccess(currentList);
    }

    @Test
    public void undoDelete() {
        //delete the first in the list
        TestTodo[] currentList = td.getTypicalTodos();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);
        assertUndoSuccess(currentList);
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTodoFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);
        assertUndoSuccess(currentList);
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTodoFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteSuccess(targetIndex, currentList);
        assertUndoSuccess(currentList);

    }

    private void assertAddSuccess(TestTodo todoToAdd, TestTodo... currentList) {

        commandBox.runCommand(todoToAdd.getAddCommand());

        //confirm the new card contains the right data
        TodoCardHandle addedCard = todoListPanel.navigateToTodo(todoToAdd.getName().fullName);
        assertMatching(todoToAdd, addedCard);
        //confirm the list now contains all previous todos plus the new todo
        TestTodo[] expectedList = TestUtil.addTodosToList(currentList, todoToAdd);
        assertTrue(todoListPanel.isListMatching(expectedList));
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Address book has been cleared!");
    }

    private void assertUndoSuccess(TestTodo[] currentList) {
        commandBox.runCommand("undo");
        assertTrue(todoListPanel.isListMatching(currentList));
    }

    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTodo[] currentList) {
        TestTodo todoToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTodo[] expectedRemainder = TestUtil.removeTodoFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous todos except the deleted todo
        assertTrue(todoListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TODO_SUCCESS, todoToDelete));
    }

}
