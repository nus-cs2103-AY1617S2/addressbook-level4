package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

     @Test
     public void add() {
    
//     // add one task
//     TestTask[] currentList = td.getTypicalTasks();
//     TestTask taskToAdd = td.zoo;
//     assertAddSuccess(taskToAdd, currentList);
//     currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//    
//     // add another task
//     taskToAdd = td.yam;
//     assertAddSuccess(taskToAdd, currentList);
//     currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//    
//     // invalid command
//     commandBox.runCommand("adds Johnny");
//     assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
     }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel
                .navigateToTask(taskToAdd.getTaskName().fullTaskName);
        assertMatching(taskToAdd, addedCard);

        // confirm the list now contains all previous persons plus the new
        // task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
