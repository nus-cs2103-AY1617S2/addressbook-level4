package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.DeadlineTaskCardHandle;
import guitests.guihandles.EventTaskCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.testutil.TestTask;
import seedu.taskmanager.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    // @@author A0141102H
    @Test
    public void add() {
        // add one floating task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.sampleEvent;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();

        // add another task (deadline)
        taskToAdd = td.sampleDeadline;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();

        // add duplicate task
        commandBox.runCommand(td.sampleDeadline.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(eventTaskListPanel.isListMatching(currentList));
        assertTrue(deadlineTaskListPanel.isListMatching(currentList));
        assertTrue(floatingTaskListPanel.isListMatching(currentList));

        // add to empty list
        commandBox.runCommand("CLEAR");
        assertAddSuccess(td.eatBreakfast);

        // invalid command
        commandBox.runCommand("ADDS Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
/*
    @Test
    public void add_eventWithClashingTimeSlots_success() {

        String clashFeedback = "Clash with task: Index ";

        // setting up test environment
        commandBox.runCommand("CLEAR");
        commandBox.runCommand(td.eventTestMon.getOneDayEventAddCommand());
        commandBox.runCommand(td.eventTestTuesThurs.getAddCommand());
        commandBox.runCommand(td.eventTestThurs.getOneDayEventAddCommand());
        commandBox.runCommand(td.eventTestFriSat.getAddCommand());

        // add clashing event of currently existing one day event
        TestTask[] currentList = td.getTypicalTestEventsForBlockingTimeSlots();
        TestTask taskToAdd = td.sampleClashBetweenOneDayEvent;
        assertAddOneDayEventSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertResultMessage(clashFeedback + "3" + "\n" + String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n"
                + "Task added at index: 4");

        commandBox.runCommand("UNDO");
        currentList = TestUtil.removeTasksFromList(currentList, taskToAdd);

        // add clashing event between existing multiple day event
        taskToAdd = td.sampleClashBetweenMultipleDaysEvent;
        assertAddOneDayEventSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertResultMessage(clashFeedback + "2" + "\n" + String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n"
                + "Task added at index: 3");

        commandBox.runCommand("UNDO");
        // add clashing event spanning across existing multiple day event
        taskToAdd = td.sampleClashAcrossMultipleDaysEvent;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertResultMessage(clashFeedback + "3" + "\n" + String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n"
                + "Task added at index: 2");

        commandBox.runCommand("UNDO");
        // add clashing event at start of existing multiple day event
        taskToAdd = td.sampleClashStartOfMultipleDaysEvent;
        assertAddOneDayEventSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertResultMessage(clashFeedback + "3" + "\n" + String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n"
                + "Task added at index: 2");

        commandBox.runCommand("UNDO");

        // add clashing event at end of existing multiple day event
        taskToAdd = td.sampleClashEndOfMultipleDaysEvent;
        assertAddOneDayEventSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertResultMessage(clashFeedback + "2" + "\n" + String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n"
                + "Task added at index: 3");

        commandBox.runCommand("UNDO");

        // add non-clashing event on same day as existing event
        taskToAdd = td.sampleNoClashSameDayEvent;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n" + "Task added at index: 4");

        commandBox.runCommand("UNDO");
        // add non-clashing event on separate day as existing event
        taskToAdd = td.sampleNoClashSeparateDayEvent;
        assertAddOneDayEventSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd) + "\n" + "Task added at index: 5");

    }
*/
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        if (taskToAdd.isEventTask()) {
            EventTaskCardHandle addedCard = eventTaskListPanel.navigateToEventTask(taskToAdd.getTaskName().toString());
            assertMatching(taskToAdd, addedCard);
        } else {
            if (taskToAdd.isDeadlineTask()) {
                DeadlineTaskCardHandle addedCard = deadlineTaskListPanel
                        .navigateToDeadlineTask(taskToAdd.getTaskName().toString());
                assertMatching(taskToAdd, addedCard);
            } else {
                if (taskToAdd.isFloatingTask()) {
                    FloatingTaskCardHandle addedCard = floatingTaskListPanel
                            .navigateToFloatingTask(taskToAdd.getTaskName().toString());
                    assertMatching(taskToAdd, addedCard);
                }
            }
        }

        // confirm the list now contains all previous tasks plus the new
        // task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertTrue(eventTaskListPanel.isListMatching(expectedList));
        assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));
    }
/*
    private void assertAddOneDayEventSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getOneDayEventAddCommand());

        // confirm the new card contains the right data
        if (taskToAdd.isEventTask()) {
            EventTaskCardHandle addedCard = eventTaskListPanel.navigateToEventTask(taskToAdd.getTaskName().toString());
            assertMatching(taskToAdd, addedCard);
        } else {
            if (taskToAdd.isDeadlineTask()) {
                DeadlineTaskCardHandle addedCard = deadlineTaskListPanel
                        .navigateToDeadlineTask(taskToAdd.getTaskName().toString());
                assertMatching(taskToAdd, addedCard);
            } else {
                if (taskToAdd.isFloatingTask()) {
                    FloatingTaskCardHandle addedCard = floatingTaskListPanel
                            .navigateToFloatingTask(taskToAdd.getTaskName().toString());
                    assertMatching(taskToAdd, addedCard);
                }
            }
        }

        // confirm the list now contains all previous tasks plus the new
        // task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertTrue(eventTaskListPanel.isListMatching(expectedList));
        assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));
    }
*/
}
