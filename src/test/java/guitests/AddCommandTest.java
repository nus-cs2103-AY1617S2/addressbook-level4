package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskCardHandle;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

//@@author A0148038A
/*
 * GUI test for AddCommand
 */
public class AddCommandTest extends WhatsLeftGuiTest {

    @Test
    public void addEvent() {
        //add one event
        TestEvent[] currentList = te.getTypicalEvents();
        TestEvent eventToAdd = te.consultation;
        assertAddEventSuccess(eventToAdd, currentList);
        currentList = TestUtil.addEventsToList(currentList, eventToAdd);

        //add another event
        eventToAdd = te.workshop;
        assertAddEventSuccess(eventToAdd, currentList);
        currentList = TestUtil.addEventsToList(currentList, eventToAdd);

        //add duplicate event
        commandBox.runCommand(te.consultation.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ACTIVITY);
        assertTrue(eventListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear ev");
        assertAddEventSuccess(te.workshop);

        //invalid command
        commandBox.runCommand("adds invalid event");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    public void addTask() {
        //add one task
        TestTask[] currentList = tt.getTypicalTasks();
        TestTask taskToAdd = tt.homework;
        assertAddTaskSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = tt.cycling;
        assertAddTaskSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(tt.homework.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ACTIVITY);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear ts");
        assertAddTaskSuccess(tt.cycling);

        //invalid command
        commandBox.runCommand("adds invalid task");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddEventSuccess(TestEvent eventToAdd, TestEvent... currentList) {
        commandBox.runCommand(eventToAdd.getAddCommand());

        //confirm the new card contains the right data
        EventCardHandle addedCard = eventListPanel.navigateToEvent(eventToAdd.getDescription().description);
        assertMatchingEvent(eventToAdd, addedCard);

        //confirm the list now contains all previous events plus the new event
        TestEvent[] expectedList = TestUtil.addEventsToList(currentList, eventToAdd);
        assertTrue(eventListPanel.isListMatching(expectedList));
    }

    private void assertAddTaskSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDescription().description);
        assertMatchingTask(taskToAdd, addedCard);

        //confirm the list now contains all previous events plus the new event
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
