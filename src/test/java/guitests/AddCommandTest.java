package guitests;

//import static org.junit.Assert.assertTrue;

import org.junit.Test;

//import guitests.guihandles.TaskCardHandle;
//import seedu.onetwodo.commons.core.Messages;
//import seedu.onetwodo.logic.commands.AddCommand;
//import seedu.onetwodo.logic.commands.ClearCommand;
//import seedu.onetwodo.testutil.TestTask;
//import seedu.onetwodo.testutil.TestUtil;

public class AddCommandTest extends ToDoListGuiTest {

    @Test
    public void add() {
/*      

        TODO: write add tests here. Use td.getTypicalTasks for testing.
        TODO: command back import if needed.
        Suggestion: 1) Add multiple tasks with same TaskType.
                    2) Add duplicated task.
                    3) Clear task and try add again.
                    
        TestTask[] currentList = td.getTypicalTasks();

        //add one task
        TestTask taskToAdd = td.task1;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.task2;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.task1.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertAddSuccess(td.taskA);

        //invalid command
        commandBox.runCommand(AddCommand.COMMAND_WORD +" some tasks");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
*/
    }

/*      
        private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        //TODO: confirms that the successful add result is what we expected
    }
*/
}
