package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.todolist.logic.commands.DeleteCommand.MESSAGE_DELETE_TODO_SUCCESS;

import org.junit.Test;

import seedu.todolist.testutil.TestTodo;
import seedu.todolist.testutil.TestUtil;

public class DeleteCommandTest extends TodoListGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestTodo[] currentList = td.getTypicalTodos();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTodoFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTodoFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The todo index provided is invalid");

    }

    /**
     * Runs the delete command to delete the todo at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first todo in the list,
     * @param currentList A copy of the current list of todos (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTodo[] currentList) {
        TestTodo todoToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTodo[] expectedRemainder = TestUtil.removeTodoFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous todos except the deleted todo
        assertTrue(todoListPanel.isListMatching(true, expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TODO_SUCCESS, todoToDelete));
    }

}
