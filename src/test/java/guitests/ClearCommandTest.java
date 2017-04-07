package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.logic.commands.ClearCommand;
import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0148038A
/*
 * GUI test for ClearCommand
 */
public class ClearCommandTest extends WhatsLeftGuiTest {

    @Test
    public void clearEvent() {
        //verify a non-empty event list can be cleared
        TestEvent[] currentEventList = te.getTypicalEvents();
        TestEvent[] filteredEventList = TestUtil.getFilteredTestEvents(currentEventList);
        assertTrue(eventListPanel.isListMatching(filteredEventList));
        assertClearEventCommandSuccess();

        //verify other commands can work after a clear event command
        currentEventList = TestUtil.removeEventsFromList(currentEventList, currentEventList);
        TestEvent eventToAdd = te.workshop;
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        filteredEventList = TestUtil.getFilteredTestEvents(currentEventList);
        commandBox.runCommand(eventToAdd.getAddCommand());
        assertTrue(eventListPanel.isListMatching(filteredEventList));
        commandBox.runCommand("delete ev 1");
        assertEventListSize(0);

        //invalid command
        commandBox.runCommand("clear invalid event");
        assertResultMessage("Invalid command format! \n" + ClearCommand.MESSAGE_USAGE);

        //verify clear command works when the list is empty
        assertClearEventCommandSuccess();
    }

    @Test
    public void clearTask() {
        //verify a non-empty task list can be cleared
        TestTask[] currentTaskList = tt.getTypicalTasks();
        TestTask[] filteredTaskList = TestUtil.getFilteredTestTasks(currentTaskList);
        assertTrue(taskListPanel.isListMatching(filteredTaskList));
        assertClearTaskCommandSuccess();

        //verify other commands can work after a clear task command
        currentTaskList = TestUtil.removeTasksFromList(currentTaskList, currentTaskList);
        TestTask taskToAdd = tt.homework;
        currentTaskList = TestUtil.addTasksToList(currentTaskList, taskToAdd);
        filteredTaskList = TestUtil.getFilteredTestTasks(currentTaskList);
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertTrue(taskListPanel.isListMatching(filteredTaskList));
        commandBox.runCommand("delete ts 1");
        assertTaskListSize(0);

        //verify clear command works when the list is empty
        assertClearTaskCommandSuccess();

        //invalid command
        commandBox.runCommand("clear invalid task");
        assertResultMessage("Invalid command format! \n" + ClearCommand.MESSAGE_USAGE);
    }

    @Test
    public void clearAll() {
        //verify a non-empty WhatsLeft can be cleared
        TestEvent[] currentEventList = te.getTypicalEvents();
        TestEvent[] filteredEventList = TestUtil.getFilteredTestEvents(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        TestTask[] filteredTaskList = TestUtil.getFilteredTestTasks(currentTaskList);
        assertTrue(eventListPanel.isListMatching(filteredEventList));
        assertTrue(taskListPanel.isListMatching(filteredTaskList));
        assertClearAllCommandSuccess();

        //verify other commands can work after a clear command
        //verify add and delete event works after a clear command
        currentEventList = TestUtil.removeEventsFromList(currentEventList, currentEventList);
        TestEvent eventToAdd = te.workshop;
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        filteredEventList = TestUtil.getFilteredTestEvents(currentEventList);
        commandBox.runCommand(eventToAdd.getAddCommand());
        assertTrue(eventListPanel.isListMatching(filteredEventList));
        commandBox.runCommand("delete ev 1");
        assertEventListSize(0);

        //verify add and delete task works after a clear command
        currentTaskList = TestUtil.removeTasksFromList(currentTaskList, currentTaskList);
        TestTask taskToAdd = tt.homework;
        currentTaskList = TestUtil.addTasksToList(currentTaskList, taskToAdd);
        filteredTaskList = TestUtil.getFilteredTestTasks(currentTaskList);
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertTrue(taskListPanel.isListMatching(filteredTaskList));
        commandBox.runCommand("delete ts 1");
        assertTaskListSize(0);

        //invalid command
        commandBox.runCommand("clear all");
        assertResultMessage("Invalid command format! \n" + ClearCommand.MESSAGE_USAGE);

        //verify clear command works when the list is empty
        assertClearAllCommandSuccess();
    }

    private void assertClearEventCommandSuccess() {
        commandBox.runCommand("clear ev");
        assertEventListSize(0);
        assertResultMessage("Event list in WhatsLeft has been cleared!");
    }

    private void assertClearTaskCommandSuccess() {
        commandBox.runCommand("clear ts");
        assertTaskListSize(0);
        assertResultMessage("Task list in WhatsLeft has been cleared!");
    }

    private void assertClearAllCommandSuccess() {
        commandBox.runCommand("clear");
        assertEventListSize(0);
        assertTaskListSize(0);
        assertResultMessage("WhatsLeft has been cleared!");
    }
}
