package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskCardHandle;

import seedu.whatsleft.commons.core.Messages;
import seedu.whatsleft.logic.commands.AddCommand;
import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0148038A
/*
 * GUI test for AddCommand
 */
public class AddCommandTest extends WhatsLeftGuiTest {

    @Test
    public void addEvent() {
        //add one event
        TestEvent[] currentEventList = te.getTypicalEvents();
        TestEvent eventToAdd = te.consultation;
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        assertAddEventSuccess(eventToAdd, currentEventList);

        //add another event
        eventToAdd = te.workshop;
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        assertAddEventSuccess(eventToAdd, currentEventList);

        //add duplicate event
        commandBox.runCommand(eventToAdd.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ACTIVITY);
        assertTrue(eventListPanel.isListMatching(TestUtil.getFilteredTestEvents(currentEventList)));

        //add a clashed event
        eventToAdd = te.clashedWorkshop;
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        assertAddEventSuccess(eventToAdd, currentEventList);
        assertResultMessage("New event added but with possible clash! : " + eventToAdd.getAsText());

        //add an event with invalid end date time
        commandBox.runCommand("add CS2102 Demostration st/0800 sd/060717 et/0900 ed/050717");
        assertResultMessage(AddCommand.MESSAGE_ILLEGAL_EVENT_END_DATETIME);

        //add to empty list
        commandBox.runCommand("clear ev");
        currentEventList = TestUtil.removeEventsFromList(currentEventList, currentEventList);
        eventToAdd = te.presentation;
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        assertAddEventSuccess(eventToAdd, currentEventList);

        //case insensitive command
        eventToAdd = te.consultation;
        commandBox.runCommand("aDD MA2101 Consultation st/1000 sd/270817 et/1100 l/S17");
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        assertTrue(eventListPanel.isListMatching(TestUtil.getFilteredTestEvents(currentEventList)));

        //invalid command
        commandBox.runCommand("adds invalid event");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

    }

    @Test
    public void addTask() {
        //add one task
        TestTask[] currentTaskList = tt.getTypicalTasks();
        TestTask taskToAdd = tt.homework;
        currentTaskList = TestUtil.addTasksToList(currentTaskList, taskToAdd);
        assertAddTaskSuccess(taskToAdd, currentTaskList);

        //add another task
        taskToAdd = tt.cycling;
        currentTaskList = TestUtil.addTasksToList(currentTaskList, taskToAdd);
        assertAddTaskSuccess(taskToAdd, currentTaskList);

        //add duplicate task
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ACTIVITY);
        assertTrue(taskListPanel.isListMatching(TestUtil.getFilteredTestTasks(currentTaskList)));

        //add to empty list
        commandBox.runCommand("clear ts");
        currentTaskList = TestUtil.removeTasksFromList(currentTaskList, currentTaskList);
        currentTaskList = TestUtil.addTasksToList(currentTaskList, taskToAdd);
        assertAddTaskSuccess(taskToAdd, currentTaskList);

        //case insensitive command
        taskToAdd = tt.homework;
        commandBox.runCommand("aDD MA2101 HW p/high bt/1100 bd/270617 l/S17 ta/homework");
        currentTaskList = TestUtil.addTasksToList(currentTaskList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(TestUtil.getFilteredTestTasks(currentTaskList)));

        //invalid command
        commandBox.runCommand("adds invalid task");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddEventSuccess(TestEvent eventToAdd, TestEvent... currentList) {
        commandBox.runCommand(eventToAdd.getAddCommand());

        //confirm the new card contains the right data
        EventCardHandle addedCard = eventListPanel.navigateToEvent(eventToAdd.getAsText());
        assertMatchingEvent(eventToAdd, addedCard);

        //confirm the list now contains all previous events plus the new event
        TestEvent[] filteredEventList = TestUtil.getFilteredTestEvents(currentList);
        assertTrue(eventListPanel.isListMatching(filteredEventList));
    }

    private void assertAddTaskSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getAsText());
        assertMatchingTask(taskToAdd, addedCard);

        //confirm the list now contains all previous events plus the new task
        TestTask[] filteredTaskList = TestUtil.getFilteredTestTasks(currentList);
        assertTrue(taskListPanel.isListMatching(filteredTaskList));
    }

}
