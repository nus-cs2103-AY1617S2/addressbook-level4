package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class AddCommandTest extends AddressBookGuiTest {
    @Test
    public void add() {
        // add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.ida;
        // System.out.println(currentList[0]);
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // //add another task
        taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        ////
        // //add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());

        assertTrue(taskListPanel.isListMatching(currentList));
        ////
        //// //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);
        //// //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        //@@author A0164212U
        //
        // //invalid timing order
        //start date = end date (start time > end time)
        commandBox.runCommand("add timeOrderTest sd/11:45 ed/10:45");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        //start date > end date (same time)
        commandBox.runCommand("add timeOrderTest sd/10:45 26/03/2017 ed/10:45 25/03/2016");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        //start date > end date (no time)
        commandBox.runCommand("add timeOrderTest sd/3/8/2017 ed/25/03/2016");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        //start date > end date (start time > end time)
        commandBox.runCommand("add timeOrderTest sd/10:45 1/1/2017 ed/9:45 1/1/2016");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        //start date > end date (start time < end time)
        commandBox.runCommand("add timeOrderTest sd/8:45 05/06/2012 ed/9:45 02/05/2012");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        //@@author
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        for (int i = 0; i < currentList.length; i++) {
            commandBox.runCommand(currentList[i].getAddCommand());
        }
        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDescription().toString());
        assertMatching(taskToAdd, addedCard);

        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
