package guitests;

//import org.controlsfx.control.PropertySheet.Item;

//import static org.junit.Assert.assertTrue;
//import static seedu.onetwodo.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
//import seedu.onetwodo.testutil.TestTask;
//import seedu.onetwodo.testutil.TestUtil;

public class DeleteCommandTest extends ToDoListGuiTest {

    @Test
    public void delete() {
/*      

        TODO: write delete tests here. Use td.getTypicalTasks for testing.
        TODO: command back import if needed.
        Suggestion: 1) Delete first item from any TaskType
                    2) Delete last item same TaskType
                    3) Delete the item that was just deleted
*/            
        
        //TestTask[] currentList = td.getTypicalTasks();
        
        // invalid index
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e99999");
        assertResultMessage("The task index provided is invalid");
        
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e-1");
        assertResultMessage("The task index provided is invalid");
        
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e0");
        assertResultMessage("The task index provided is invalid");
        
        // empty list
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e1");
        assertResultMessage("The task index provided is invalid");
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
/*    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
         TestTask taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
         TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
         commandBox.runCommand("delete " + targetIndexOneIndexed);

         //confirm the list now contains all previous tasks except the deleted task
         assertTrue(taskListPanel.isListMatching(expectedRemainder));

         //confirm the result message is correct
         assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
         
         TODO: Make a method that checks if delete is success.
         Suggestion: 1) check if deleted item is really gone/ result list is as expected
                     2) check if delete result message is what we expected.
    }
*/
}
