//package guitests;
//
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//
//import guitests.guihandles.EventCardHandle;
//import guitests.guihandles.TaskCardHandle;
//
//import seedu.whatsleft.commons.core.Messages;
//import seedu.whatsleft.logic.commands.AddCommand;
//import seedu.whatsleft.testutil.TestEvent;
//import seedu.whatsleft.testutil.TestTask;
//import seedu.whatsleft.testutil.TestUtil;
//
////@@author A0148038A
///*
// * GUI test for AddCommand
// */
//public class AddCommandTest extends WhatsLeftGuiTest {
//
//    @Test
//    public void addEvent() {
//        //add one event
//        TestEvent[] currentList = te.getTypicalEvents();
//        TestEvent eventToAdd = te.consultation;
//        currentList = TestUtil.addEventsToList(currentList, eventToAdd);
//        assertAddEventSuccess(eventToAdd, currentList);
//
//        //add another event
//        eventToAdd = te.workshop;
//        currentList = TestUtil.addEventsToList(currentList, eventToAdd);
//        assertAddEventSuccess(eventToAdd, currentList);
//
//        //add duplicate event
//        commandBox.runCommand(eventToAdd.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ACTIVITY);
//        assertTrue(eventListPanel.isListMatching(TestUtil.getFilteredTestEvents(currentList)));
//
//        //add a clashed event
//        eventToAdd = te.clashedWorkshop;
//        currentList = TestUtil.addEventsToList(currentList, eventToAdd);
//        assertAddEventSuccess(eventToAdd, currentList);
//        assertResultMessage("New event added but with possible clash! : " + eventToAdd.getAsText());
//
//        //add to empty list
//        commandBox.runCommand("clear ev");
//        currentList = TestUtil.removeEventsFromList(currentList, currentList);
//        eventToAdd = te.presentation;
//        currentList = TestUtil.addEventsToList(currentList, eventToAdd);
//        assertAddEventSuccess(eventToAdd, currentList);
//
//        //case insensitive command
//        eventToAdd = te.consultation;
//        commandBox.runCommand("aDD MA2101 Consultation st/1000 sd/270817 et/1100 l/S17");
//        currentList = TestUtil.addEventsToList(currentList, eventToAdd);
//        assertTrue(eventListPanel.isListMatching(TestUtil.getFilteredTestEvents(currentList)));
//
//        //invalid command
//        commandBox.runCommand("adds invalid event");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
//
//    }
//
//    @Test
//    public void addTask() {
//        //add one task
//        TestTask[] currentList = tt.getTypicalTasks();
//        TestTask taskToAdd = tt.homework;
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        assertAddTaskSuccess(taskToAdd, currentList);
//
//        //add another task
//        taskToAdd = tt.cycling;
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        assertAddTaskSuccess(taskToAdd, currentList);
//
//        //add duplicate task
//        commandBox.runCommand(taskToAdd.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ACTIVITY);
//        assertTrue(taskListPanel.isListMatching(TestUtil.getFilteredTestTasks(currentList)));
//
//        //add to empty list
//        commandBox.runCommand("clear ts");
//        currentList = TestUtil.removeTasksFromList(currentList, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        assertAddTaskSuccess(taskToAdd, currentList);
//
//        //case insensitive command
//        taskToAdd = tt.homework;
//        commandBox.runCommand("aDD MA2101 HW p/high bt/1100 bd/270617 l/S17 ta/homework");
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        assertTrue(taskListPanel.isListMatching(TestUtil.getFilteredTestTasks(currentList)));
//
//        //invalid command
//        commandBox.runCommand("adds invalid task");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    private void assertAddEventSuccess(TestEvent eventToAdd, TestEvent... currentList) {
//        commandBox.runCommand(eventToAdd.getAddCommand());
//
//        //confirm the new card contains the right data
//        EventCardHandle addedCard = eventListPanel.navigateToEvent(eventToAdd.getAsText());
//        assertMatchingEvent(eventToAdd, addedCard);
//
//        //confirm the list now contains all previous events plus the new event
//        TestEvent[] filteredList = TestUtil.getFilteredTestEvents(currentList);
//        assertTrue(eventListPanel.isListMatching(filteredList));
//    }
//
//    private void assertAddTaskSuccess(TestTask taskToAdd, TestTask... currentList) {
//        commandBox.runCommand(taskToAdd.getAddCommand());
//
//        //confirm the new card contains the right data
//        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getAsText());
//        assertMatchingTask(taskToAdd, addedCard);
//
//        //confirm the list now contains all previous events plus the new task
//        TestTask[] filteredList = TestUtil.getFilteredTestTasks(currentList);
//        assertTrue(taskListPanel.isListMatching(filteredList));
//    }
//
//}
