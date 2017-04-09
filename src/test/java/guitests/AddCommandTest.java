package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

     @Test
     public void add() {
    	 try {
			setup();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     commandBox.runCommand("clear");
     // add one task
     TestTask[] currentList = td.getTypicalTasks();
     TestTask taskToAdd = td.zoo;
     assertAddSuccess(taskToAdd, currentList);
     currentList = TestUtil.addTasksToList(currentList, taskToAdd);

     // add another person
     taskToAdd = td.yam;
     assertAddSuccess(taskToAdd, currentList);
     currentList = TestUtil.addTasksToList(currentList, taskToAdd);

     // invalid command
     commandBox.runCommand("adds Johnny");
     assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
     }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        //TaskCardHandle addedCard = taskListPanel
        //        .navigateToTask(taskToAdd.getTaskName().fullTaskName);
        Task t1 = new Task(taskToAdd.getTaskName(), taskToAdd.getTaskDate(), taskToAdd.getTaskStartTime(),
        			taskToAdd.getTaskEndTime(), taskToAdd.getTaskDescription(), taskToAdd.getTaskStatus());
        ReadOnlyTask temp = taskListPanel.getPerson(taskListPanel.getNumberOfPeople() - 1);
        Task t2 = new Task(temp);
        assertTrue(t1.equals(t2));
        //assertMatching(taskToAdd, addedCard);

        // confirm the list now contains all previous persons plus the new
        // task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
