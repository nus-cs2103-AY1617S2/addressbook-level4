package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.model.task.Timing;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class AddCommandTest extends AddressBookGuiTest {
    @Test
    public void add() {
        // add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.ida;
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
        commandBox.runCommand("add timeOrderTest sd/03/08/2017 ed/25/03/2016");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        //start date > end date (start time > end time)
        commandBox.runCommand("add timeOrderTest sd/10:45 01/01/2017 ed/09:45 01/01/2016");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        //start date > end date (start time < end time)
        commandBox.runCommand("add timeOrderTest sd/08:45 05/06/2012 ed/09:45 02/05/2012");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        // //invalid syntax for timing
        commandBox.runCommand("add timeSyntaxTest sd/1:45");
        assertResultMessage(Timing.MESSAGE_TIMING_CONSTRAINTS);
        //should be 01:45
        commandBox.runCommand("add timeSyntaxTest sd/01:45 1/01/2017");
        assertResultMessage(Timing.MESSAGE_TIMING_CONSTRAINTS);
        //should be 01/01/2017 for date
        commandBox.runCommand("add timeSyntaxTest sd/01:45 01/01/2017 ed/3:15 01/01/2017");
        assertResultMessage(Timing.MESSAGE_TIMING_CONSTRAINTS);
        //should be 03:15
        commandBox.runCommand("add timeSyntaxTest sd/01:45 01/01/2017 ed/03:15 01/2/2017");
        assertResultMessage(Timing.MESSAGE_TIMING_CONSTRAINTS);
        //should be 01/02/2017
        //@@author
    }

    @Test
    public void addRecurringTask() {
        TestTask[] currentList = td.getEmptyTasks();
        TestTask taskToAdd = td.recMonth;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(currentList));
    }


    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand("clear");
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
