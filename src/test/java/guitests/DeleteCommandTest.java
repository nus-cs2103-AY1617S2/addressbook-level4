package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.whatsleft.logic.commands.DeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS;
import static seedu.whatsleft.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0148038A
/*
 * GUI test for DeleteCommand
 */
public class DeleteCommandTest extends WhatsLeftGuiTest {

    @Test
    public void deleteEvent() {

        //delete the first in the event list
        TestEvent[] currentEventList =  TestUtil.getFilteredTestEvents(te.getTypicalEvents());
        TestEvent[] filteredEventList = TestUtil.getFilteredTestEvents(currentEventList);
        int targetIndex = 1;
        assertDeleteEventSuccess(targetIndex, filteredEventList);

        //delete the last in the event list
        filteredEventList = TestUtil.removeEventFromList(filteredEventList, targetIndex);
        targetIndex = filteredEventList.length;
        assertDeleteEventSuccess(targetIndex, filteredEventList);

        //delete from the middle of the event list
        filteredEventList = TestUtil.removeEventFromList(filteredEventList, targetIndex);
        targetIndex = filteredEventList.length / 2;
        assertDeleteEventSuccess(targetIndex, filteredEventList);

        //invalid index
        commandBox.runCommand("delete ev " + filteredEventList.length + 1);
        assertResultMessage("The Event index provided is invalid");

    }

    @Test
    public void deleteTask() {

        //delete the first in the task list
        TestTask[] currentTaskList =  TestUtil.getFilteredTestTasks(tt.getTypicalTasks());
        TestTask[] filteredTaskList = TestUtil.getFilteredTestTasks(currentTaskList);
        int targetIndex = 1;
        assertDeleteTaskSuccess(targetIndex, filteredTaskList);

        //delete the last in the task list
        filteredTaskList = TestUtil.removeTaskFromList(filteredTaskList, targetIndex);
        targetIndex = filteredTaskList.length;
        assertDeleteTaskSuccess(targetIndex, filteredTaskList);

        //delete from the middle of the task list
        filteredTaskList = TestUtil.removeTaskFromList(filteredTaskList, targetIndex);
        targetIndex = filteredTaskList.length / 2;
        assertDeleteTaskSuccess(targetIndex, filteredTaskList);

        //invalid index
        commandBox.runCommand("delete ts " + filteredTaskList.length + 1);
        assertResultMessage("The Task index provided is invalid");

    }

    /**
     * Runs the delete event command to delete the event at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first event in the event list,
     * @param currentList A copy of the current list of events (before deletion).
     */
    private void assertDeleteEventSuccess(int targetIndexOneIndexed, final TestEvent[] filteredEventList) {
        TestEvent eventToDelete = filteredEventList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestEvent[] expectedEventList = TestUtil.removeEventFromList(filteredEventList, targetIndexOneIndexed);
        TestEvent[] expectedFilteredEventList = TestUtil.getFilteredTestEvents(expectedEventList);

        commandBox.runCommand("delete ev " + targetIndexOneIndexed);

        //confirm the list now contains all previous events except the deleted event
        assertTrue(eventListPanel.isListMatching(expectedFilteredEventList));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    /**
     * Runs the delete event command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the task list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteTaskSuccess(int targetIndexOneIndexed, final TestTask[] filteredTaskList) {
        TestTask taskToDelete = filteredTaskList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTask[] expectedTaskList = TestUtil.removeTaskFromList(filteredTaskList, targetIndexOneIndexed);
        TestTask[] expectedFilteredTaskList = TestUtil.getFilteredTestTasks(expectedTaskList);

        commandBox.runCommand("delete ts " + targetIndexOneIndexed);

        //confirm the list now contains all previous events except the deleted event
        assertTrue(taskListPanel.isListMatching(expectedFilteredTaskList));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
